package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.MealRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MealContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.AddMealState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.MealState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealViewModel(
    private val mealRepository: MealRepository
) : ViewModel(), MealContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealState: MutableLiveData<MealState> = MutableLiveData()
    override val addDone: MutableLiveData<AddMealState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                mealRepository
                    .getAllByCategory(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMeals() {
        val subscription = mealRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealById(id: String) {
        publishSubject.onNext(id)
    }

    override fun addMeal(meal: Meal) {
        val subscription = mealRepository
            .insert(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddMealState.Success
                },
                {
                    addDone.value = AddMealState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

}
package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.Resource
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.RecipeRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.AddRecipeState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel(), MainContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val recipesState: MutableLiveData<RecipesState> = MutableLiveData()
    override val addDone: MutableLiveData<AddRecipeState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                recipeRepository
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
                    recipesState.value = RecipesState.Success(it)
                },
                {
                    recipesState.value = RecipesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun searchRecipes(q: String, page: Int) {
        val subscription = recipeRepository
            .search(q, page)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> recipesState.value = RecipesState.Loading
                        is Resource.Success -> recipesState.value = RecipesState.DataFetched
                        is Resource.Error -> recipesState.value = RecipesState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    recipesState.value = RecipesState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllRecipes() {
        val subscription = recipeRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    recipesState.value = RecipesState.Success(it)
                },
                {
                    recipesState.value = RecipesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getRecipesByCategory(category: String) {
        publishSubject.onNext(category)
    }

    override fun addRecipe(recipe: Recipe) {
        val subscription = recipeRepository
            .insert(recipe)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddRecipeState.Success
                },
                {
                    addDone.value = AddRecipeState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}
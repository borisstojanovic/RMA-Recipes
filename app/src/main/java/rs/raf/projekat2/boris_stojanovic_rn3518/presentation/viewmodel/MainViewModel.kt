package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.Resource
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.CategoryRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.RecipeRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.CategoryState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.SingleRecipeState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel(), MainContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val recipesState: MutableLiveData<RecipesState> = MutableLiveData()
    override val recipeState: MutableLiveData<SingleRecipeState> = MutableLiveData()
    override val categoryState: MutableLiveData<CategoryState> = MutableLiveData()

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

    override fun fetchAllCategories() {
        val subscription = categoryRepository
                .fetchAll()
                .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when(it) {
                                is Resource.Loading -> categoryState.value = CategoryState.Loading
                                is Resource.Success -> categoryState.value = CategoryState.DataFetched
                                is Resource.Error -> categoryState.value = CategoryState.Error("Error happened while fetching data from the server")
                            }
                        },
                        {
                            categoryState.value = CategoryState.Error("Error happened while fetching data from the server")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getAllCategories() {
        val subscription = categoryRepository
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            categoryState.value = CategoryState.Success(it)
                        },
                        {
                            categoryState.value = CategoryState.Error("Error happened while fetching data from db")
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

    override fun findRecipeById(rId: String) {
        val subscription = recipeRepository
                .find(rId)
                .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            when(it) {
                                is Resource.Loading -> recipeState.value = SingleRecipeState.Loading
                                is Resource.Success -> recipeState.value = SingleRecipeState.DataFetched
                                is Resource.Error -> recipeState.value = SingleRecipeState.Error("Error happened while fetching data from the server")
                            }
                        },
                        {
                            recipeState.value = SingleRecipeState.Error("Error happened while fetching data from the server")
                            Timber.e(it)
                        }
                )
        subscriptions.add(subscription)
    }

    override fun getRecipeById(id: String) {
        val subscription = recipeRepository
                .getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            recipeState.value = SingleRecipeState.Success(it)
                        },
                        {
                            recipeState.value = SingleRecipeState.Error("Error happened while fetching data from db")
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
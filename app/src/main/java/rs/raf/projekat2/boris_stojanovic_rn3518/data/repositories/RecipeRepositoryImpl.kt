package rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.RecipesDao
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.remote.RecipeService
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeEntity
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.Resource

class RecipeRepositoryImpl(
    private val localDataSource: RecipesDao,
    private val remoteDataSource: RecipeService
) : RecipeRepository {

    override fun search(q: String, page: Int): Observable<Resource<Unit>> {
        return remoteDataSource
            .search(q, page)
            .doOnNext{
                val entities = it.recipes.map {
                    RecipeEntity(
                        it.id,
                        it.imageUrl,
                        it.title,
                        listOf(),
                        q
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun find(rId: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .find(rId)
            .doOnNext{
                val entity =
                    RecipeEntity(
                        it.id,
                        it.imageUrl,
                        it.title,
                        it.ingredients,
                        ""
                    )
                localDataSource.update(entity)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Recipe>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Recipe(
                        it.id,
                        it.title,
                        it.imageUrl,
                        it.category
                    )
                }
            }
    }

    override fun getAllByCategory(category: String): Observable<List<Recipe>> {
        return localDataSource
            .getByCategory(category)
            .map {
                it.map {
                    Recipe(
                        it.id,
                        it.title,
                        it.imageUrl,
                        it.category
                    )
                }
            }
    }

    override fun insert(recipe: Recipe): Completable {
        val recipeEntity =
            RecipeEntity(
                recipe.id,
                recipe.imageUrl,
                recipe.title,
                listOf(),
                recipe.category
            )
        return localDataSource
            .insert(recipeEntity)
    }

}
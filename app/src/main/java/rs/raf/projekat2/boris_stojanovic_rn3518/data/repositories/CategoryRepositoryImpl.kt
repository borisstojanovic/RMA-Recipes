package rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.CategoryDao
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.remote.CategoryService
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeEntity
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.Resource
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.Category
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.CategoryEntity

class CategoryRepositoryImpl(
    private val localDataSource: CategoryDao,
    private val remoteDataSource: CategoryService
) : CategoryRepository {

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll()
            .doOnNext {
                val entities = it.categories.map {
                    CategoryEntity(
                        it.title,
                        it.imageUrl
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAll(): Observable<List<Category>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Category(
                        it.title,
                        it.imageUrl
                    )
                }
            }
    }

    override fun insert(category: Category): Completable {
        val categoryEntity =
            CategoryEntity(
                category.title,
                category.imageUrl,
            )
        return localDataSource
            .insert(categoryEntity)
    }

}
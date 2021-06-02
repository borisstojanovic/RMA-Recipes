package rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.MealsDao
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.MealEntity

class MealRepositoryImpl(
    private val localDataSource: MealsDao
) : MealRepository {

    override fun getAll(): Observable<List<Meal>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Meal(
                        it.id,
                        it.title,
                        it.imageUrl,
                        it.rId,
                        it.mealDate,
                        it.category
                    )
                }
            }
    }

    override fun getAllByCategory(category: String): Observable<List<Meal>> {
        return localDataSource
            .getByCategory(category)
            .map {
                it.map {
                    Meal(
                        it.id,
                        it.title,
                        it.imageUrl,
                        it.rId,
                        it.mealDate,
                        it.category
                    )
                }
            }
    }

    override fun insert(meal: Meal): Completable {
        val mealEntity =
            MealEntity(
                meal.id,
                meal.title,
                meal.imageUrl,
                meal.date,
                meal.category,
                meal.rId,
            )
        return localDataSource
            .insert(mealEntity)
    }

}
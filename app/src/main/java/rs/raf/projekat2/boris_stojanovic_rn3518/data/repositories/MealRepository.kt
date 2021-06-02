package rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal

interface MealRepository {

    fun getAll(): Observable<List<Meal>>
    fun getAllByCategory(category: String): Observable<List<Meal>>
    fun insert(meal: Meal): Completable

}
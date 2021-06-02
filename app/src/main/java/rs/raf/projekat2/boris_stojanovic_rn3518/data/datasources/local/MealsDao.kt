package rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.MealEntity

@Dao
abstract class MealsDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MealEntity>): Completable

    @Query("SELECT * FROM meals")
    abstract fun getAll(): Observable<List<MealEntity>>

    @Query("DELETE FROM meals")
    abstract fun deleteAll()

    @Query("SELECT * FROM meals WHERE id = :id")
    abstract fun getById(id: Int): Observable<MealEntity>

    @Query("SELECT * FROM meals WHERE category_name LIKE :category || '%'")
    abstract fun getByCategory(category: String): Observable<List<MealEntity>>

}
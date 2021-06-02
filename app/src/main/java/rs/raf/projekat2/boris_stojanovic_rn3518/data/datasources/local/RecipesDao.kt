package rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeEntity
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeWithMeals

@Dao
abstract class RecipesDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: RecipeEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<RecipeEntity>): Completable

    @Query("SELECT * FROM recipes")
    abstract fun getAll(): Observable<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    abstract fun getById(id: String): Observable<RecipeEntity>

    @Query("DELETE FROM recipes")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<RecipeEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Update
    abstract fun update(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :category || '%'")
    abstract fun getByCategory(category: String): Observable<List<RecipeEntity>>

    @Transaction
    @Query("SELECT * FROM recipes")
    abstract fun getAllWithMeals(): Observable<List<RecipeWithMeals>>
}
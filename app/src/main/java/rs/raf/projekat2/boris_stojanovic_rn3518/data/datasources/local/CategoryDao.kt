package rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.CategoryEntity
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeEntity

@Dao
abstract class CategoryDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: CategoryEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<CategoryEntity>): Completable

    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>

    @Query("DELETE FROM categories")
    abstract fun deleteAll()

    @Query("SELECT * FROM categories WHERE title like :title")
    abstract fun getByTitle(title: String): Observable<CategoryEntity>

    @Transaction
    open fun deleteAndInsertAll(entities: List<CategoryEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}
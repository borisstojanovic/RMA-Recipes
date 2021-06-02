package rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.Resource

interface RecipeRepository {

    fun search(q:String, page: Int): Observable<Resource<Unit>>
    fun find(rId: String): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Recipe>>
    fun getAllByCategory(category: String): Observable<List<Recipe>>
    fun insert(recipe: Recipe): Completable

}
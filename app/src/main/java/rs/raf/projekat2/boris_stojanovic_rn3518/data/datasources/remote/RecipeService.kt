package rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeDetailsResponse
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeResponse
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeResponseItem

interface RecipeService {

    @GET("v2/recipes")
    fun search(@Query("q") q: String, @Query("page") page: Int): Observable<RecipeResponse>

    @GET("get")
    fun find(@Query("rId") rId: String): Observable<RecipeDetailsResponse>
}
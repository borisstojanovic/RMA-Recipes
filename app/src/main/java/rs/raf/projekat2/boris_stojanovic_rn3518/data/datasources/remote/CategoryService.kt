package rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.CategoryResponse

interface CategoryService {

    @GET("categories")
    fun getAll(@Query("limit") limit: Int = 250): Observable<CategoryResponse>

}
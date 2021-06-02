package rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.Resource
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.Category

interface CategoryRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Category>>
    fun insert(category: Category): Completable

}
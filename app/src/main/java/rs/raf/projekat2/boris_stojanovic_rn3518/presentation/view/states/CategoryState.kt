package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states

import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.Category

sealed class CategoryState {
    object Loading: CategoryState()
    object DataFetched: CategoryState()
    data class Success(val categories: List<Category>): CategoryState()
    data class Error(val message: String): CategoryState()
}
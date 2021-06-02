package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.AddRecipeState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.CategoryState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState

interface CategoryContract {

    interface ViewModel {
        val categoryState: LiveData<CategoryState>

        fun fetchAllCategories()
        fun getAllCategories()
    }

}
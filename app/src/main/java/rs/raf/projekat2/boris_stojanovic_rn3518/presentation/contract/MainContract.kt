package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.CategoryState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.SingleRecipeState

interface MainContract {

    interface ViewModel {

        val categoryState: LiveData<CategoryState>
        val recipesState: LiveData<RecipesState>
        val recipeState: LiveData<SingleRecipeState>

        fun fetchAllCategories()
        fun getAllCategories()
        fun searchRecipes(q: String, page: Int)
        fun getAllRecipes()
        fun getRecipesByCategory(category: String)
        fun findRecipeById(rId: String)
        fun getRecipeById(id: String)
    }

}
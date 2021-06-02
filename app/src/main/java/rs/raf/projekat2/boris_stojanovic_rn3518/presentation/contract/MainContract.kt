package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.SingleRecipeState

interface MainContract {

    interface ViewModel {

        val recipesState: LiveData<RecipesState>
        val recipeState: LiveData<SingleRecipeState>

        fun searchRecipes(q: String, page: Int)
        fun getAllRecipes()
        fun getRecipesByCategory(category: String)
        fun findRecipeById(rId: String)
        fun getRecipeById(id: String)
    }

}
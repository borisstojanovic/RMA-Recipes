package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.AddRecipeState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState

interface MainContract {

    interface ViewModel {

        val recipesState: LiveData<RecipesState>
        val addDone: LiveData<AddRecipeState>

        fun searchRecipes(q: String, page: Int)
        fun getAllRecipes()
        fun getRecipesByCategory(category: String)
        fun addRecipe(recipe: Recipe)
    }

}
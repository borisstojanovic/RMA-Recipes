package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states

import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe

sealed class SingleRecipeState {
    object Loading: SingleRecipeState()
    object DataFetched: SingleRecipeState()
    data class Success(val recipe: Recipe): SingleRecipeState()
    data class Error(val message: String): SingleRecipeState()
}
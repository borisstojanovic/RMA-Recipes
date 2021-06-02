package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states

import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe

sealed class RecipesState {
    object Loading: RecipesState()
    object DataFetched: RecipesState()
    data class Success(val recipes: List<Recipe>): RecipesState()
    data class Error(val message: String): RecipesState()
}
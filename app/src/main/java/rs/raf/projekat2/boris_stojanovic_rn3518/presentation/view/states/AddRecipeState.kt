package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states

sealed class AddRecipeState {
    object Success: AddRecipeState()
    data class Error(val message: String): AddRecipeState()
}
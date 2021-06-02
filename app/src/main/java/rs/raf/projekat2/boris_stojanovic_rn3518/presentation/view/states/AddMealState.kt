package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states

sealed class AddMealState {
    object Success: AddMealState()
    data class Error(val message: String): AddMealState()
}
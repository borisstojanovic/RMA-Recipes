package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states

import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal

sealed class MealState {
    object Loading: MealState()
    data class Success(val meals: List<Meal>): MealState()
    data class Error(val message: String): MealState()
}
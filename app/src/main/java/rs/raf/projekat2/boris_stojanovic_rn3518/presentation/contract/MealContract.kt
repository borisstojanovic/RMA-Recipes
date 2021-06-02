package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.AddMealState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.MealState

interface MealContract {

    interface ViewModel {

        val mealState: LiveData<MealState>
        val addDone: LiveData<AddMealState>

        fun getAllMeals()
        fun getMealById(id: String)
        fun addMeal(meal: Meal)
    }

}
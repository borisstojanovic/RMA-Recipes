package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe

class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {

    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.title == newItem.title
    }

}
package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.koin.experimental.builder.getArguments
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.Category
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemMealBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.diff.MealDiffCallback
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.viewholder.MealViewHolder

class MealAdapter(private val listener: (Meal) -> Unit) : ListAdapter<Meal, MealViewHolder>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemBinding = LayoutItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

}
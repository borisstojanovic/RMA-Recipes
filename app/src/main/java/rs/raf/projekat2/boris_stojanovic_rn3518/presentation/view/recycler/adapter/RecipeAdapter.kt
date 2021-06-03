package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemRecipeBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.diff.RecipeDiffCallback
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.viewholder.RecipeViewHolder

class RecipeAdapter(private val listener: (Recipe) -> Unit) : ListAdapter<Recipe, RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = LayoutItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

}
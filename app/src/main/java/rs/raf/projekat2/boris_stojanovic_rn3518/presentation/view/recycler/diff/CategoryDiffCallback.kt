package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.Category
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

}
package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.Category
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemCategoryBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemRecipeBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter(private val listener: (Category) -> Unit) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = LayoutItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

}
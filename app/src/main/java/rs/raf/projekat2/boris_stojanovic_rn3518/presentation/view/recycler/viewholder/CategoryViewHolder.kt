package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.Category
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemCategoryBinding

class CategoryViewHolder(private val itemBinding: LayoutItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: Category) {
        itemBinding.titleTv.text = category.title
        Glide.with(itemBinding.root).load(category.imageUrl).into(itemBinding.listRvImageView)
    }

}
package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemMovieBinding

class RecipeViewHolder(private val itemBinding: LayoutItemMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipe: Recipe) {
        itemBinding.titleTv.text = recipe.title
        Glide.with(itemBinding.root).load(recipe.imageUrl).into(itemBinding.listRvImageView)
    }

}
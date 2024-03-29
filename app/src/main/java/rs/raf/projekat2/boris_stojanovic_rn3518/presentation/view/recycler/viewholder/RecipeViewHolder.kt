package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemRecipeBinding

class RecipeViewHolder(private val itemBinding: LayoutItemRecipeBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipe: Recipe) {
        itemBinding.recipeTitleTv.text = recipe.title
        itemBinding.recipePublisherTv.text = recipe.publisher
        itemBinding.recipeSocialIdTv.text = recipe.socialUrl.toInt().toString()
        Glide.with(itemBinding.root).load(recipe.imageUrl).into(itemBinding.listRvImageView)
    }

}
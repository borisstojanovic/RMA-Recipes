package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.viewholder

import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.LayoutItemMealBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class MealViewHolder(private val itemBinding: LayoutItemMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: Meal) {
        itemBinding.titleTv.text = meal.title
        val format = SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault())
        itemBinding.dateTv.text = format.format(meal.date)
        itemBinding.categoryTv.text = meal.category
        loadImage(meal.imageUrl)
    }

    private fun loadImage(path: String) {
        try {
            val f = File(path)
            if(f.exists()) {
                val b = BitmapFactory.decodeStream(FileInputStream(f))
                Glide.with(itemBinding.root).load(b).placeholder(R.drawable.ic_launcher_background).into(itemBinding.listRvImageView)
            }else{
                Glide.with(itemBinding.root).load(path).into(itemBinding.listRvImageView)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Glide.with(itemBinding.root).load(path).into(itemBinding.listRvImageView)
        }
    }

}
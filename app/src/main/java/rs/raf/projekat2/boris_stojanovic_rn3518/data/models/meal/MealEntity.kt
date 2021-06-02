package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val imageUrl: String,
    @ColumnInfo(name = "meal_date")var mealDate: Date,
    @ColumnInfo(name = "category_name") val category: String,
    @ColumnInfo(name = "recipe_id") val rId: String
)
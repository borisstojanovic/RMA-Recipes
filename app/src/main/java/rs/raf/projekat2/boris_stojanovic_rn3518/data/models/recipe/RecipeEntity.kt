package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val imageUrl: String,
    val title : String,
    val ingredients: List<String>,
    @ColumnInfo(name = "category_title") val category: String
)
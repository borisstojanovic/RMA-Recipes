package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val imageUrl: String
)
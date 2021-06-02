package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal

import java.util.*

data class Meal(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val rId: String,
    val date: Date,
    val category: String
)
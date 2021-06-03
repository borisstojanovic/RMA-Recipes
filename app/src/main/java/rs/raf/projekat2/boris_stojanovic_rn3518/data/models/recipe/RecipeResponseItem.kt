package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe

data class RecipeResponseItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val socialUrl: Double,
    val sourceUrl: String,
    val publisher: String,
    val publishedId: String
)
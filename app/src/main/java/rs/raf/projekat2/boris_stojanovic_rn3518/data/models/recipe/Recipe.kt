package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe

data class Recipe(
    val id: String,
    val imageUrl: String,
    val title : String,
    val socialUrl: Double,
    val publisher: String,
    val ingredients: List<String>,
)
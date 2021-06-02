package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe

data class RecipeDetails(
    val id: String,
    val title: String,
    val imageUrl: String,
    val category: String,
    val ingredients: List<String>
)
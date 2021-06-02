package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe

data class RecipeDetailsResponseItem(
    val recipe_id: String,
    val title: String,
    val image_url: String,
    val ingredients: List<String>
)
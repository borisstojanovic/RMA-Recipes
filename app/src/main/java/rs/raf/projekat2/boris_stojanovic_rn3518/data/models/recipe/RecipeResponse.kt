package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe

data class RecipeResponse(
    val count: Int,
    val recipes: List<RecipeResponseItem>
)
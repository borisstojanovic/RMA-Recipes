package rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe

import androidx.room.Embedded
import androidx.room.Relation
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.MealEntity

data class RecipeWithMeals(
    @Embedded val recipeEntity: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipe_id"
    )
    val courses: List<MealEntity>?
)
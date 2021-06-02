package rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.converters.DateConverter
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.converters.StringListConverter
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.category.CategoryEntity
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.MealEntity
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.RecipeEntity

@Database(
    entities = [RecipeEntity::class, MealEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(StringListConverter::class, DateConverter::class)
abstract class RecipeDataBase : RoomDatabase() {
    abstract fun getRecipeDao(): RecipesDao
    abstract fun getMealDao(): MealsDao
    abstract fun getCategoryDao(): CategoryDao
}
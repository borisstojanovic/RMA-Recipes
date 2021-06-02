package rs.raf.projekat2.boris_stojanovic_rn3518.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.RecipeDataBase
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.MealRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.MealRepositoryImpl
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MealViewModel

val mealModule = module {

    viewModel { MealViewModel(mealRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localDataSource = get()) }

    single { get<RecipeDataBase>().getMealDao() }

}


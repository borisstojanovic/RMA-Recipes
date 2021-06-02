package rs.raf.projekat2.boris_stojanovic_rn3518.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.RecipeDataBase
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.remote.RecipeService
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.RecipeRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.RecipeRepositoryImpl
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel

val recipeModule = module {

    viewModel { MainViewModel(recipeRepository = get()) }

    single<RecipeRepository> { RecipeRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<RecipeDataBase>().getRecipeDao() }

    single<RecipeService> { create(retrofit = get()) }

}


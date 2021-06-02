package rs.raf.projekat2.boris_stojanovic_rn3518.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.local.RecipeDataBase
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.remote.CategoryService
import rs.raf.projekat2.boris_stojanovic_rn3518.data.datasources.remote.RecipeService
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.CategoryRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.CategoryRepositoryImpl
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.RecipeRepository
import rs.raf.projekat2.boris_stojanovic_rn3518.data.repositories.RecipeRepositoryImpl
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.CategoryViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel

val categoryModule = module {

    viewModel { CategoryViewModel(categoryRepository = get()) }

    single<CategoryRepository> { CategoryRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<RecipeDataBase>().getCategoryDao() }

    single<CategoryService> { create(retrofit = get()) }

}


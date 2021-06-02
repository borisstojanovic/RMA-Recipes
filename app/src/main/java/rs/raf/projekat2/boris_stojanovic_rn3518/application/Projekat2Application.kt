package rs.raf.projekat2.boris_stojanovic_rn3518.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import rs.raf.projekat2.boris_stojanovic_rn3518.modules.categoryModule
import rs.raf.projekat2.boris_stojanovic_rn3518.modules.coreModule
import rs.raf.projekat2.boris_stojanovic_rn3518.modules.mealModule
import rs.raf.projekat2.boris_stojanovic_rn3518.modules.recipeModule
import timber.log.Timber

class Projekat2Application : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = listOf(
            coreModule,
            recipeModule,
            mealModule,
            categoryModule
        )
        startKoin {
            androidLogger(Level.ERROR)
            // Use application context
            androidContext(this@Projekat2Application)
            // Use properties from assets/koin.properties
            androidFileProperties()
            // Use koin fragment factory for fragment instantiation
            fragmentFactory()
            // modules
            modules(modules)
        }
    }

}
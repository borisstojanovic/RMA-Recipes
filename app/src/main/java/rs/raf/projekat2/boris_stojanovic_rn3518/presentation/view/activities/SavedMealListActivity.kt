package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.FragmentManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityMainBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityRecipeDetailsBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivitySavedMealsBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MealContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.ListFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.RecipeDetailsFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.SavedMealsListFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MealViewModel

class SavedMealListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedMealsBinding

    private val mealViewModel: MealContract.ViewModel by viewModel<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.findItem(R.id.categoriesMenuItem)?.setOnMenuItemClickListener {
            val intent = Intent (this, CategoryListActivity::class.java)
            startActivity(intent)
            finish()
            return@setOnMenuItemClickListener true
        }
        menu?.findItem(R.id.savedMealsMenuItem)?.setOnMenuItemClickListener {
            val intent = Intent (this, SavedMealListActivity::class.java)
            startActivity(intent)
            finish()
            return@setOnMenuItemClickListener true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        val mgr = supportFragmentManager
        if (mgr.backStackEntryCount == 0) {
            super.onBackPressed();
        } else {
            mgr.popBackStack();
        }
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        mealViewModel.getAllMeals()
        supportFragmentManager.beginTransaction()
            .add(R.id.fcvSavedMealsListActivity, SavedMealsListFragment())
            .addToBackStack(null)
            .commit()
    }
}

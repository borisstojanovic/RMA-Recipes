package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.FragmentManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityAddMealBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityMainBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityRecipeDetailsBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MealContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.AddMealFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.ListFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.RecipeDetailsFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MealViewModel

class AddMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMealBinding

    private val mealViewModel: MealContract.ViewModel by viewModel<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
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

    private fun init() {
        initUi()
    }

    private fun initUi() {
        val rId = intent.getStringExtra("rId")
        mealViewModel.getRecipeById(rId.orEmpty())
        supportFragmentManager.beginTransaction()
            .add(R.id.fcvAddMealActivity, AddMealFragment())
            .commit()
    }
}

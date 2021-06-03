package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityCategoryListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.ListFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel

class CategoryListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryListBinding

    private val mainViewModel: MainContract.ViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryListBinding.inflate(layoutInflater)
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
        mainViewModel.fetchAllCategories()
        mainViewModel.getAllCategories()
        supportFragmentManager.beginTransaction()
            .add(R.id.fcvCategoryList, ListFragment())
            .commit()
    }
}

package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityMainBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.ListFragment
import rs.raf.vezbe11.presentation.view.adapters.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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

    }
}

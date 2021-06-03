package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityMainBinding
import rs.raf.vezbe11.presentation.view.adapters.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
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
        initNavigation()
    }

    private fun initUi() {
        binding.viewPager.adapter =
                MainPagerAdapter(
                        supportFragmentManager,
                        this
                )
    }
    private fun initNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_1 -> binding.viewPager.setCurrentItem(MainPagerAdapter.FRAGMENT_1, false)
                R.id.navigation_2 -> binding.viewPager.setCurrentItem(MainPagerAdapter.FRAGMENT_2, false)
            }
            true
        }
    }
}

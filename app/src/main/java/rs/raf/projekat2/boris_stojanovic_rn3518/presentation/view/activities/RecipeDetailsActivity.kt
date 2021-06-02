package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityMainBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.ActivityRecipeDetailsBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.ListFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments.RecipeDetailsFragment
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    private val mainViewModel: MainContract.ViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        val rId = intent.getStringExtra("rId")
        mainViewModel.findRecipeById(rId.orEmpty())
        mainViewModel.getRecipeById(rId.orEmpty())
        supportFragmentManager.beginTransaction()
            .add(R.id.fcvRecipeDetailsActivity, RecipeDetailsFragment())
            .commit()
    }
}

package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentRecipeDetailsBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities.AddMealActivity
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities.RecipeDetailsActivity
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.SingleRecipeState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import timber.log.Timber

class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentRecipeDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initObservers()
    }


    private fun initObservers() {
        mainViewModel.recipeState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun renderState(state: SingleRecipeState) {
        when (state) {
            is SingleRecipeState.Success -> {
                showLoadingState(false)
                Glide.with(this).load(state.recipe.imageUrl).into(binding.recipeDetailsImageView)
                binding.recipeDetailsTitleTV.text = state.recipe.title
                binding.recipeDetailsIngredientsTV.movementMethod = ScrollingMovementMethod()
                binding.addRecipeButton.setOnClickListener {
                    activity?.let{
                        val intent = Intent (it, AddMealActivity::class.java)
                        intent.putExtra("rId", state.recipe.id)
                        it.startActivity(intent)
                    }
                }
                for (element in state.recipe.ingredients) {
                    binding.recipeDetailsIngredientsTV.append(element)
                    binding.recipeDetailsIngredientsTV.append("\n")
                }
            }
            is SingleRecipeState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SingleRecipeState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SingleRecipeState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.textView2.isVisible = !loading
        binding.recipeDetailsIngredientsTV.isVisible = !loading
        binding.recipeDetailsTitleTV.isVisible = !loading
        binding.recipeDetailsImageView.isVisible = !loading
        binding.recipeDetailsTitleTV.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
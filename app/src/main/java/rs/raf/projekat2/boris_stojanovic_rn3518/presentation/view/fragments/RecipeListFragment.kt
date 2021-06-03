package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentRecipeListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities.RecipeDetailsActivity
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.RecipeAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import timber.log.Timber

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentRecipeListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initSearchBar()
        initRecycler()
        initListeners()
    }

    private fun initSearchBar() {
        binding.recipeSearchBar.focusable = View.NOT_FOCUSABLE
        binding.recipeSearchBar.isIconified = false
        binding.recipeSearchBar.clearFocus()
    }

    private fun initRecycler() {
        binding.recipeListRv.layoutManager = LinearLayoutManager(context)

        adapter = RecipeAdapter{ item ->
            activity?.let{
                val intent = Intent (it, RecipeDetailsActivity::class.java)
                intent.putExtra("rId", item.id)
                it.startActivity(intent)
            }
        }

        binding.recipeListRv.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding.recipeListRv.context, resources.configuration.orientation)
        binding.recipeListRv.addItemDecoration(dividerItemDecoration);
    }

    private fun initListeners() {
        binding.recipeSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null && !query.toString().isBlank()) {
                    mainViewModel.searchRecipes(query.toString(), 1)
                    mainViewModel.getRecipesByCategory(query.toString())
                    return true
                }else {
                    return false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initObservers() {
        mainViewModel.recipesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun renderState(state: RecipesState) {
        when (state) {
            is RecipesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.recipes)
            }
            is RecipesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is RecipesState.DataFetched -> {
                showLoadingState(false)
            }
            is RecipesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.recipeSearchBar.isVisible = !loading
        binding.recipeListRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
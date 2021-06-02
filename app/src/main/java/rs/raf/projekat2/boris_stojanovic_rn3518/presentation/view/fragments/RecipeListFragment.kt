package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentRecipeListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.CategoryContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.RecipeAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.CategoryState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.CategoryViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import timber.log.Timber

class RecipeListFragment : Fragment(R.layout.fragment_recipe_list) {

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()
    private var category: String? = ""

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
        category = arguments?.getString("category")
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = RecipeAdapter()
        binding.listRv.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding.listRv.context, resources.configuration.orientation);
        binding.listRv.addItemDecoration(dividerItemDecoration);
    }

    private fun initListeners() {
        binding.inputEt.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                mainViewModel.searchRecipes(v.text.toString(), 1)
                mainViewModel.getRecipesByCategory(v.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun initObservers() {
        mainViewModel.recipesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mainViewModel.searchRecipes(category.orEmpty(), 1)
        mainViewModel.getRecipesByCategory(category.orEmpty())
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
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is RecipesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.inputEt.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
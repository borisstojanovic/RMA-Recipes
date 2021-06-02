package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.ext.scope
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.CategoryContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.RecipeAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.CategoryState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.RecipesState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.CategoryViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import timber.log.Timber

class ListFragment : Fragment(R.layout.fragment_list) {

    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()

    private var _binding: FragmentListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
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
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter { item ->
            val recipeListFragment = RecipeListFragment()
            val bundle = Bundle()
            bundle.putString("category", item.title)
            recipeListFragment.arguments = bundle
            val ft = activity?.supportFragmentManager?.beginTransaction();
            ft?.replace(R.id.fcvMainActivity, recipeListFragment);
            ft?.commit();
        };
        binding.listRv.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding.listRv.context, resources.configuration.orientation);
        binding.listRv.addItemDecoration(dividerItemDecoration);
    }

    private fun initListeners() {
        binding.inputEt.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                val recipeListFragment = RecipeListFragment()
                val bundle = Bundle()
                bundle.putString("category", v.text.toString())
                recipeListFragment.arguments = bundle
                val ft = activity?.supportFragmentManager?.beginTransaction();
                ft?.replace(R.id.fcvMainActivity, recipeListFragment);
                ft?.commit();
                true
            } else {
                false
            }
        }
    }

    private fun initObservers() {
        categoryViewModel.categoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        categoryViewModel.getAllCategories()
        categoryViewModel.fetchAllCategories()
    }

    private fun renderState(state: CategoryState) {
        when (state) {
            is CategoryState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.categories)
            }
            is CategoryState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CategoryState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is CategoryState.Loading -> {
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
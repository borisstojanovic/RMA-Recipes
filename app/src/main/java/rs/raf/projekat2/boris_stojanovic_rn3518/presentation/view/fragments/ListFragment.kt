package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

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
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.CategoryState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import timber.log.Timber

class ListFragment : Fragment(R.layout.fragment_list) {

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

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
        initSearchBar()
        initRecycler()
        initListeners()
    }

    private fun initSearchBar() {
        binding.searchBar.focusable = View.NOT_FOCUSABLE
        binding.searchBar.isIconified = false
        binding.searchBar.clearFocus()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter { item ->
            mainViewModel.searchRecipes(item.title, 1)
            mainViewModel.getRecipesByCategory(item.title)
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
            ft?.replace(R.id.fcvContainer, RecipeListFragment())
            ft?.addToBackStack(null)
            ft?.commit()
        }
        binding.listRv.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding.listRv.context, resources.configuration.orientation);
        binding.listRv.addItemDecoration(dividerItemDecoration);
    }

    private fun initListeners() {

        binding.searchBar.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null && !query.toString().isBlank()) {
                    mainViewModel.searchRecipes(query.toString(), 1)
                    mainViewModel.getRecipesByCategory(query.toString())
                    val ft = activity?.supportFragmentManager?.beginTransaction()
                    ft?.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    ft?.replace(R.id.fcvContainer, RecipeListFragment())
                    ft?.addToBackStack(null)
                    ft?.commit()
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
        mainViewModel.categoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mainViewModel.getAllCategories()
        mainViewModel.fetchAllCategories()
    }

    private fun renderState(state: CategoryState) {
        when (state) {
            is CategoryState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.categories)
            }
            is CategoryState.Error -> {
                showLoadingState(false)
            }
            is CategoryState.DataFetched -> {
                showLoadingState(false)
            }
            is CategoryState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.searchBar.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

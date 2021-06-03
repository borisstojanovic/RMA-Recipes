package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
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
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentSavedMealsListBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MealContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.activities.RecipeDetailsActivity
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.recycler.adapter.MealAdapter
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.MealState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MealViewModel
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class SavedMealsListFragment : Fragment(R.layout.fragment_saved_meals_list) {

    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()

    private var _binding: FragmentSavedMealsListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedMealsListBinding.inflate(inflater, container, false)
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
        adapter = MealAdapter{ item ->
            activity?.let{
                val intent = Intent (it, RecipeDetailsActivity::class.java)
                intent.putExtra("rId", item.rId)
                it.startActivity(intent)
            }
        };
        binding.listRv.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(binding.listRv.context, resources.configuration.orientation);
        binding.listRv.addItemDecoration(dividerItemDecoration);
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        mealViewModel.mealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun renderState(state: MealState) {
        when (state) {
            is MealState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.meals)
            }
            is MealState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
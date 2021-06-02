package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.recipe.Recipe
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentInputBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MainContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.AddRecipeState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MainViewModel
import java.lang.Long
import java.util.*

class InputFragment : Fragment(R.layout.fragment_input) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentInputBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
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
        binding.addBtn.setOnClickListener {
            val input = binding.inputEt.text.toString()
            if (input.isBlank()) {
                Toast.makeText(context, "Input cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.inputEt.text.clear()

            val temp: UUID = UUID.randomUUID()

            val recipeToAdd =
                Recipe(
                    id = Long.toHexString(temp.mostSignificantBits)
                            + Long.toHexString(temp.leastSignificantBits),
                    title = input,
                    imageUrl = "",
                    category = ""
                )
            mainViewModel.addRecipe(recipeToAdd)
        }
    }

    private fun initObservers() {
        mainViewModel.addDone.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: AddRecipeState) {
        when(state) {
            is AddRecipeState.Success -> Toast.makeText(context, "Movie added", Toast.LENGTH_SHORT)
                .show()
            is AddRecipeState.Error -> Toast.makeText(context, "Error happened", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
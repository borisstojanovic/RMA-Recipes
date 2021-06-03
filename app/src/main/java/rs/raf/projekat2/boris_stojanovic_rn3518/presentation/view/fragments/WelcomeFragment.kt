package rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat2.boris_stojanovic_rn3518.R
import rs.raf.projekat2.boris_stojanovic_rn3518.data.models.meal.Meal
import rs.raf.projekat2.boris_stojanovic_rn3518.databinding.FragmentAddMealBinding
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.contract.MealContract
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.AddMealState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.view.states.SingleRecipeState
import rs.raf.projekat2.boris_stojanovic_rn3518.presentation.viewmodel.MealViewModel
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class WelcomeFragment : Fragment(R.layout.fragment_welcome){
    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()

    private var imagePath: String? = null

    private val getPicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val bundle = it.data?.extras
            val finalPhoto: Bitmap = bundle?.get("data") as Bitmap
            val fileName = Calendar.getInstance().timeInMillis.toString() + "slika"
            val filePath = saveToInternalStorage(finalPhoto, fileName)
            if (filePath != null) {
                loadImageFromStorage(filePath, fileName)
            }
        }
    }
    private var _binding: FragmentAddMealBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMealBinding.inflate(inflater, container, false)
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
        initViews()
        initListeners()
    }

    private fun initViews() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        binding.datePickerButton.text = getTodaysDate()
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.categories,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.spinner.adapter = adapter
            }
        }
    }

    private fun initListeners() {
        binding.addMealImageView.setOnClickListener {
            val builder: AlertDialog.Builder? = activity?.let {
                AlertDialog.Builder(it)
            }

            builder?.setMessage("Do you want to start your camera app?")
                    ?.setTitle("Camera Dialog")
            builder?.apply {
                setPositiveButton("Ok",
                        DialogInterface.OnClickListener { dialog, id ->
                            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            try {
                                getPicture.launch(takePictureIntent)
                            }catch (e: ActivityNotFoundException) {
                                Toast.makeText(context, "Camera app not found!", Toast.LENGTH_SHORT).show()
                            }
                        })
                setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
            }
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()

        }
        binding.datePickerButton.setOnClickListener{
            initDatePicker()
        }
    }

    private fun initObservers() {
        mealViewModel.recipeState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mealViewModel.addDone.observe(viewLifecycleOwner, Observer{
            Timber.e(it.toString())
            renderAddState(it)
        })
    }

    private fun renderAddState(state: AddMealState) {
        when(state) {
            is AddMealState.Success -> Toast.makeText(context, "Meal added", Toast.LENGTH_SHORT)
                    .show()
            is AddMealState.Error -> Toast.makeText(context, "Error happened", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    private fun renderState(state: SingleRecipeState) {
        when (state) {
            is SingleRecipeState.Success -> {
                showLoadingState(false)
                Glide.with(this).load(state.recipe.imageUrl).into(binding.addMealImageView)
                binding.mealTitleTextView.text = state.recipe.title
                binding.addMealSaveButton.setOnClickListener{
                    val temp: UUID = UUID.randomUUID()
                    if(this.imagePath == null){
                        this.imagePath = state.recipe.imageUrl
                    }
                    val mealToAdd = Meal(
                            id = temp.mostSignificantBits + temp.leastSignificantBits,
                            title = binding.mealTitleTextView.text.toString(),
                            category = binding.spinner.selectedItem.toString(),
                            rId = state.recipe.id,
                            imageUrl = this.imagePath!!,
                            date = Date.from(this.makeDateFromString(binding.datePickerButton.text.toString()).
                                    atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    )
                    mealViewModel.addMeal(mealToAdd)
                }
            }
            is SingleRecipeState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SingleRecipeState.DataFetched -> {
                showLoadingState(false)
            }
            is SingleRecipeState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.addMealImageView.isVisible = !loading
        binding.addMealSaveButton.isVisible = !loading
        binding.mealTitleTextView.isVisible = !loading
        binding.spinner.isVisible = !loading
        binding.datePickerButton.isVisible = !loading
        binding.dateTextView.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap, fileName: String): String? {
        val cw = ContextWrapper(context?.applicationContext)
        // path to /data/data/yourapp/app_data/imageDir
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val myPath = File(directory, "$fileName.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
            this.imagePath = myPath.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    private fun loadImageFromStorage(path: String, fileName: String) {
        try {
            val f = File(path, "$fileName.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            Glide.with(this).load(b).placeholder(R.drawable.ic_launcher_background).into(binding.addMealImageView)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun getTodaysDate(): String? {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month += 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    private fun initDatePicker() {
        val dateSetListener = OnDateSetListener { datePicker, year, month, day ->
            val newMonth = month + 1
            val date = makeDateString(day, newMonth, year)
            binding.datePickerButton.text = date
        }
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val style: Int = AlertDialog.THEME_HOLO_LIGHT
        val datePickerDialog = context?.let { DatePickerDialog(it, style, dateSetListener, year, month, day) }
        datePickerDialog?.show()
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return  getMonthFormat(month) + " " + getDayFormat(day) + " " + year
    }

    private fun makeDateFromString(stringDate: String) : LocalDate{
        val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return LocalDate.parse(stringDate, formatter)
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) return "Jan"
        if (month == 2) return "Feb"
        if (month == 3) return "Mar"
        if (month == 4) return "Apr"
        if (month == 5) return "May"
        if (month == 6) return "Jun"
        if (month == 7) return "Jul"
        if (month == 8) return "Aug"
        if (month == 9) return "Sep"
        if (month == 10) return "Oct"
        if (month == 11) return "Nov"
        return if (month == 12) "Dec" else "Jan"

    }

    private fun getDayFormat(day: Int) : String{
        if(day < 10)return "0$day"
        return "" + day
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


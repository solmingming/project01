package com.example.myapplication_2.ui.dashboard

import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication_2.R
import com.example.myapplication_2.data.IngredientStore
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.databinding.FragmentDashboardBinding
import com.example.myapplication_2.ui.model.Recipe
import com.google.android.material.chip.Chip

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val selectedIngredients = mutableListOf<String>()
    private var currentImageUri: Uri? = null
    private lateinit var imageUploadButton: ImageButton
    private lateinit var submitButton: Button
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private lateinit var adapter: ArrayAdapter<String>
    private var stepCount = 1
    private var isImageUploaded = false

    private val savedRecipeList = mutableListOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUploadButton = binding.imageUpload
        submitButton = binding.submitButton

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                imageUploadButton.setImageURI(uri)
                isImageUploaded = true
                updateSubmitButtonState()
            }
        }

        submitButton.isEnabled = false
        submitButton.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.sol_gray)
        )

        imageUploadButton.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.editTextMenuName.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSubmitButtonState()
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        binding.ratingBar.setOnRatingBarChangeListener { _, _, _ ->
            updateSubmitButtonState()
        }

        val inputView: AutoCompleteTextView = binding.multiSearch
        val chipGroup = binding.chipGroup

        // ✅ IngredientStore에서 가져와 어댑터 설정
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, IngredientStore.ingredients)
        inputView.setAdapter(adapter)

        inputView.setOnItemClickListener { parent, _, position, _ ->
            val selected = parent.getItemAtPosition(position) as String
            handleIngredientInput(selected, chipGroup, inputView)
        }

        inputView.setOnEditorActionListener { _, _, _ ->
            val inputText = inputView.text.toString().trim()
            if (inputText.isNotEmpty()) {
                handleIngredientInput(inputText, chipGroup, inputView)
            }
            true
        }

        observeInitialStepEditTexts()

        binding.addStepButton.setOnClickListener {
            stepCount++
            val newEditText = EditText(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 24
                }
                background = ContextCompat.getDrawable(context, R.drawable.edittext_background)
                hint = "step $stepCount. 레시피를 작성해주세요"
                textSize = 10f
                setPadding(24, 24, 24, 24)
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            }
            newEditText.addTextChangedListener { updateSubmitButtonState() }
            binding.stepContainer.addView(newEditText, binding.stepContainer.childCount - 1)
        }

        submitButton.setOnClickListener {
            val title = binding.editTextMenuName.text.toString()
            val rating = binding.ratingBar.rating.toInt()
            val imageFileName = currentImageUri?.lastPathSegment ?: "default.jpg"

            val steps = mutableListOf<String>()
            for (i in 0 until binding.stepContainer.childCount) {
                val view = binding.stepContainer.getChildAt(i)
                if (view is EditText && view.text.isNotBlank()) {
                    steps.add(view.text.toString())
                }
            }

            val recipe = Recipe(
                imageFileName = imageFileName,
                imageUri = currentImageUri,
                title = title,
                description = steps.toList(),
                rating = rating,
                author = "달콤한 피자",
                ingredients = selectedIngredients.toList()
            )

            savedRecipeList.add(recipe)
            RecipeRepository.addRecipe(recipe)

            Toast.makeText(requireContext(), "레시피가 저장되었어요!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_notifications)

            val activity = requireActivity()
            val bottomNav = activity.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
            bottomNav.selectedItemId = R.id.navigation_notifications
        }
    }

    private fun handleIngredientInput(
        inputText: String,
        chipGroup: ViewGroup,
        inputView: AutoCompleteTextView
    ) {
        if (selectedIngredients.contains(inputText)) {
            Toast.makeText(requireContext(), "이미 선택된 재료예요!", Toast.LENGTH_SHORT).show()
        } else if (selectedIngredients.size >= 5) {
            Toast.makeText(requireContext(), "최대 5개까지만 선택할 수 있어요!", Toast.LENGTH_SHORT).show()
        } else {
            selectedIngredients.add(inputText)
            addChip(inputText, chipGroup)
            updateSubmitButtonState()

            // ✅ 자동완성 리스트에도 추가
            if (!IngredientStore.ingredients.contains(inputText)) {
                IngredientStore.ingredients.add(inputText)
                adapter.notifyDataSetChanged()

                Log.d("AutoComplete", "추가된 단어: $inputText")
                Log.d("AutoComplete", "현재 전체 재료 리스트: ${IngredientStore.ingredients}")
            }
        }

        inputView.setText("")
    }

    private fun observeInitialStepEditTexts() {
        for (i in 0 until binding.stepContainer.childCount) {
            val view = binding.stepContainer.getChildAt(i)
            if (view is EditText) {
                view.addTextChangedListener { updateSubmitButtonState() }
            }
        }
    }

    private fun updateSubmitButtonState() {
        val menuFilled = binding.editTextMenuName.text?.isNotBlank() == true
        val ingredientFilled = selectedIngredients.isNotEmpty()
        val difficultySelected = binding.ratingBar.rating > 0f

        var recipeFilled = false
        for (i in 0 until binding.stepContainer.childCount) {
            val view = binding.stepContainer.getChildAt(i)
            if (view is EditText && view.text.isNotBlank()) {
                recipeFilled = true
                break
            }
        }

        val isReady = menuFilled && ingredientFilled && difficultySelected && recipeFilled && isImageUploaded

        binding.submitButton.isEnabled = isReady
        val color = if (isReady) R.color.sol_sky_blue else R.color.sol_gray
        binding.submitButton.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), color)
        )
    }

    private fun addChip(text: String, chipGroup: ViewGroup) {
        val chip = Chip(requireContext()).apply {
            this.text = text
            isCloseIconVisible = true
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, R.color.my_chip_text))
            chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.my_chip_bg))
            chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.my_chip_stroke))
            chipStrokeWidth = 1.0f
            setPadding(24, 5, 10, 5)

            setOnCloseIconClickListener {
                chipGroup.removeView(this)
                selectedIngredients.remove(text)
                updateSubmitButtonState()
            }
        }

        chipGroup.addView(chip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

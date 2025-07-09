package com.example.myapplication_2.ui.dashboard

import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
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
import com.example.myapplication_2.utils.UserGenerator
import com.google.android.material.chip.Chip
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.bumptech.glide.Glide

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
    private var editingRecipe: Recipe? = null // ⭐ 전달된 Recipe가 있다면 수정 모드

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
            uri?.let {
                currentImageUri = it
                imageUploadButton.setImageURI(it)
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

        binding.editTextMenuName.addTextChangedListener { updateSubmitButtonState() }
        binding.ratingBar.setOnRatingBarChangeListener { _, _, _ -> updateSubmitButtonState() }

        val inputView: AutoCompleteTextView = binding.multiSearch
        val chipGroup = binding.chipGroup

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
                ).apply { bottomMargin = 24 }
                background = ContextCompat.getDrawable(context, R.drawable.edittext_background)
                hint = "step $stepCount. 레시피를 작성해주세요"
                textSize = 10f
                setPadding(24, 24, 24, 24)
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            }
            newEditText.addTextChangedListener { updateSubmitButtonState() }
            binding.stepContainer.addView(newEditText, binding.stepContainer.childCount - 1)
        }

        // ✅ 수정 모드면 데이터 채워넣기
        editingRecipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("editing_recipe", Recipe::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("editing_recipe")
        }

        editingRecipe?.let { recipe ->
            binding.editTextMenuName.setText(recipe.title)
            binding.ratingBar.rating = recipe.rating.toFloat()
            isImageUploaded = true

            // 이미지 처리
            when {
                recipe.imageUri != null -> {
                    currentImageUri = recipe.imageUri
                    imageUploadButton.setImageURI(currentImageUri)
                }
                !recipe.imageFileName.isNullOrBlank() -> {
                    val assetPath = "file:///android_asset/dishImage/${recipe.imageFileName}"
                    Glide.with(this)
                        .load(assetPath)
                        .into(imageUploadButton)
                    currentImageUri = null
                }
                else -> {
                    // 이미지 없음 (유지)
                    isImageUploaded = false
                }
            }

            // 재료
            recipe.ingredients.forEach {
                selectedIngredients.add(it)
                addChip(it, chipGroup)
            }

            // 단계
            recipe.description.forEachIndexed { index, step ->
                if (index == 0) {
                    val firstStep = binding.stepContainer.getChildAt(0) as? EditText
                    firstStep?.setText(step)
                } else {
                    stepCount++
                    val newEditText = EditText(requireContext()).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { bottomMargin = 24 }
                        background = ContextCompat.getDrawable(context, R.drawable.edittext_background)
                        hint = "step $stepCount. 레시피를 작성해주세요"
                        textSize = 10f
                        setPadding(24, 24, 24, 24)
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                        setText(step)
                    }
                    newEditText.addTextChangedListener { updateSubmitButtonState() }
                    binding.stepContainer.addView(newEditText, binding.stepContainer.childCount - 1)
                }
            }

            updateSubmitButtonState()
        }


        submitButton.setOnClickListener {
            val title = binding.editTextMenuName.text.toString()
            val rating = binding.ratingBar.rating.toInt()
            val steps = mutableListOf<String>()
            for (i in 0 until binding.stepContainer.childCount) {
                val view = binding.stepContainer.getChildAt(i)
                if (view is EditText && view.text.isNotBlank()) {
                    steps.add(view.text.toString())
                }
            }

            val currentUserName = UserGenerator.getCachedUser().name

            // 🔧 이미지 처리 로직
            val (finalImageUri, finalImageFileName) = when {
                currentImageUri != null -> currentImageUri to currentImageUri?.lastPathSegment
                !editingRecipe?.imageFileName.isNullOrBlank() -> null to editingRecipe?.imageFileName
                else -> null to "default.jpg"
            }

            // 새로운 레시피 생성
            val newRecipe = Recipe(
                imageFileName = finalImageFileName ?: "default.jpg",
                imageUri = finalImageUri,
                title = title,
                description = steps.toList(),
                rating = rating,
                author = currentUserName,
                ingredients = selectedIngredients.toList()
            )

            // 기존 레시피 제거 (수정 모드일 경우)
            editingRecipe?.let { RecipeRepository.removeRecipe(it) }

            savedRecipeList.add(newRecipe)
            RecipeRepository.addRecipe(newRecipe)

            Toast.makeText(requireContext(), "레시피가 저장되었어요!", Toast.LENGTH_SHORT).show()

            // 등록 후 Notification 화면으로 이동 + 탭 아이콘 변경
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_notifications)
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).selectedItemId =
                R.id.navigation_notifications
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

            if (!IngredientStore.ingredients.contains(inputText)) {
                IngredientStore.ingredients.add(inputText)
                adapter.notifyDataSetChanged()
                Log.d("AutoComplete", "추가된 단어: $inputText")
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
            closeIconTint = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.my_close_icon_color))

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

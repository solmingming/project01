package com.example.myapplication_2.ui.dashboard

import android.util.Log
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication_2.R
import com.example.myapplication_2.databinding.FragmentDashboardBinding
import com.google.android.material.chip.Chip
import androidx.core.widget.addTextChangedListener
import com.example.myapplication_2.ui.model.Recipe
import androidx.navigation.fragment.findNavController
import com.example.myapplication_2.data.RecipeRepository

import com.example.myapplication_2.ui.notifications.NotificationsFragment

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val selectedIngredients = mutableListOf<String>()
    private var currentImageUri: Uri? = null
    private lateinit var imageUploadButton: ImageButton
    private lateinit var submitButton: Button
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private var stepCount = 1
    private var isImageUploaded = false

    // 레시피 저장 리스트
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

        // 초기 상태 비활성화
        submitButton.isEnabled = false
        submitButton.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.sol_gray)
        )

        imageUploadButton.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.editTextMenuName.addTextChangedListener { updateSubmitButtonState() }

        binding.ratingBar.setOnRatingBarChangeListener { _, _, _ ->
            updateSubmitButtonState()
        }

        val ingredients = listOf("식용유", "계란", "당근", "양파", "마늘",
            "대파", "감자", "고추장", "간장", "소금","부추","파","두부",
            "후추", "설탕", "참기름", "된장", "두부","마라소스","팽이버섯","새송이버섯","비엔나 소세지",
            "쌀", "김치", "상추", "고추", "느타리버섯", "브로콜리", "애호박", "양배추", "청양고추", "시금치",
            "미역", "다시마", "멸치", "참치캔", "베이컨",
            "스팸", "우유", "치즈", "버터", "식빵","닭가슴살","닭다리살","통닭","조랭이떡","명란젓","소면","쯔유","밀가루","떡", "국수", "라면사리", "맛살", "깻잎","오이", "피망", "파프리카", "가지", "단호박",
            "옥수수", "방울토마토", "토마토", "부추", "열무",
            "무", "콩나물", "숙주", "김", "당면","회","양상추","버터헤드","버터","알룰로스","올리브유","투게더",
            "카레가루", "고춧가루", "국간장", "올리고당", "물엿", "메추리알", "연두부", "순두부", "소시지", "햄",
            "치킨너겟", "떡갈비", "미트볼", "핫도그", "어묵",
            "다시팩", "김가루", "참치","양념치킨소스", "데리야끼소스","꿀","소금빵","파래","홀그레인",
            "라면", "죽", "시리얼", "요거트", "잼", "김치", "볶음김치", "깍두기", "열무김치", "총각김치","소고기","새우","게",
            "쌈장", "춘장", "마요네즈", "케첩", "머스타드",
            "슬라이스치즈", "모짜렐라치즈", "체다치즈", "파마산치즈", "크림치즈",
            "플레인요거트", "그릭요거트", "시판피클", "단무지", "오징어채","완두콩","갈릭디핑소스",
            "건새우", "조미김", "자반고등어", "꽁치통조림", "고등어통조림","미원","치킨 스톡","오리엔탈 소스","렌치 소스","샐러드",
            "콩", "멸치", "계란장", "장조림","어묵","코코아 파우더","곤약","불고기","고등어","갈치","조기","돈가스",
            "가지", "애호박", "감자", "메추리알", "우엉", "식빵", "감자", "부침가루", "튀김가루",
            "모닝빵", "크로와상", "햄버거번", "냉동피자", "냉동볶음밥","흑돼지", "와규", "한우", "염소고기", "사슴고기",
            "칠면조", "오리고기", "거위고기", "캥거루고기", "악어고기", "햇반","육회","우동사리","소면","파스타면","파스타소스","토마토소스","크림소스")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ingredients)
        val inputView: AutoCompleteTextView = binding.multiSearch
        val chipGroup = binding.chipGroup

        inputView.setAdapter(adapter)

        inputView.setOnItemClickListener { parent, _, position, _ ->
            val selected = parent.getItemAtPosition(position) as String

            if (selectedIngredients.contains(selected)) {
                Toast.makeText(requireContext(), "이미 선택된 재료예요!", Toast.LENGTH_SHORT).show()
                inputView.setText("")
                return@setOnItemClickListener
            }

            if (selectedIngredients.size >= 5) {
                Toast.makeText(requireContext(), "최대 5개까지만 선택할 수 있어요!", Toast.LENGTH_SHORT).show()
                inputView.setText("")
                return@setOnItemClickListener
            }

            selectedIngredients.add(selected)
            inputView.setText("")
            addChip(selected, chipGroup)
            updateSubmitButtonState()
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
                imageUri = currentImageUri?.toString(),
                title = title,
                description = steps.toList(),
                rating = rating,
                author = "솔민이",
                ingredients = selectedIngredients.toList()
            )

            savedRecipeList.add(recipe)
            RecipeRepository.addRecipe(recipe)

            Toast.makeText(requireContext(), "레시피가 저장되었어요!", Toast.LENGTH_SHORT).show()

            // 로그는 따로 출력
            Log.d("SavedRecipe", "현재 저장된 레시피 수: ${savedRecipeList.size}")
            Log.d("SavedRecipe", "제목: ${recipe.title}, 재료: ${recipe.ingredients}, 설명: ${recipe.description}")

            // 프로필로 이동
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_notifications)

// 하단 탭 상태도 같이 변경
            val activity = requireActivity()
            val bottomNav = activity.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.nav_view)
            bottomNav.selectedItemId = R.id.navigation_notifications


        }
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

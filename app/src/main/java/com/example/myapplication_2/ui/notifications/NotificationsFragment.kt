// NotificationsFragment.kt
package com.example.myapplication_2.ui.notifications

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication_2.R
import com.example.myapplication_2.data.FridgeRepository
import com.example.myapplication_2.data.IngredientStore
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.data.sampleRecipes
import com.example.myapplication_2.databinding.FragmentNotificationsBinding
import com.example.myapplication_2.ui.model.Recipe
import com.example.myapplication_2.utils.UserGenerator
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<String>
    private var currentImageUri: Uri? = null

    private lateinit var recipeAdapter: GridRecipeAdapter
    private val recipeList = mutableListOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val user = UserGenerator.getCachedUser()  // ⬅️ 기존 generate() → getCachedUser()로 변경
        val userName = user.name                  // ⬅️ 캐시된 유저의 이름 사용

        // 프로필 이미지 및 컬러 적용
        binding.profileImage.setImageResource(user.imageResId)
        binding.profileImage.backgroundTintList = ColorStateList.valueOf(Color.parseColor(user.colorHex))

        // 유저 이름 표시
        binding.textUsername.text = "@$userName"

        // ✅ 재료 자동완성 세팅
        val inputView: AutoCompleteTextView = binding.multiSearch
        val chipGroup: ChipGroup = binding.chipGroup

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

        // 이미 저장된 냉장고 재료들 표시
        FridgeRepository.getFridge().forEach { ingredient ->
            addChip(ingredient, chipGroup)
        }

        if (RecipeRepository.getAllRecipes().isEmpty()) {
            RecipeRepository.addAll(sampleRecipes)
        }

        setupRecyclerView(userName)
    }

    private fun handleIngredientInput(
        inputText: String,
        chipGroup: ViewGroup,
        inputView: AutoCompleteTextView
    ) {
        if (FridgeRepository.getFridge().contains(inputText)) {
            Toast.makeText(requireContext(), "이미 선택된 재료예요!", Toast.LENGTH_SHORT).show()
        } else if (FridgeRepository.getFridge().size >= 5) {
            Toast.makeText(requireContext(), "최대 5개까지만 선택할 수 있어요!", Toast.LENGTH_SHORT).show()
        } else {
            FridgeRepository.addIngredient(inputText)
            addChip(inputText, chipGroup)

            if (!IngredientStore.ingredients.contains(inputText)) {
                IngredientStore.ingredients.add(inputText)
                adapter.notifyDataSetChanged()
                Log.d("AutoComplete", "➕ 추가된 단어: $inputText")
            }

            // 전체 재료 출력
            Log.d("AutoComplete", "📦 현재 전체 재료 리스트 (${IngredientStore.ingredients.size}개):")
            IngredientStore.ingredients.forEachIndexed { index, item ->
                Log.d("AutoComplete", "$index: $item")
            }
        }

        inputView.setText("")
        inputView.post { inputView.showDropDown() }
    }

    private fun addChip(text: String, chipGroup: ViewGroup) {
        val chip = Chip(requireContext()).apply {
            this.text = text
            isCloseIconVisible = true
            textSize = 12f
            setTextColor(ContextCompat.getColor(context, R.color.my_chip_text))
            chipBackgroundColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.my_chip_bg)
            )
            chipStrokeColor = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.my_chip_stroke)
            )
            closeIconTint = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.my_close_icon_color)
            )

            chipStrokeWidth = 1.0f
            setPadding(24, 5, 10, 5)

            setOnCloseIconClickListener {
                chipGroup.removeView(this)
                FridgeRepository.removeIngredient(text.toString())
            }
        }

        chipGroup.addView(chip)
    }

    private fun setupRecyclerView(userName: String) {
        val allRecipes = RecipeRepository.getAllRecipes()
        val userRecipes = allRecipes.filter { it.author == userName }
        recipeList.clear()
        recipeList.addAll(userRecipes)

        recipeAdapter = GridRecipeAdapter(recipeList)
        binding.recyclerUserRecipes.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = recipeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

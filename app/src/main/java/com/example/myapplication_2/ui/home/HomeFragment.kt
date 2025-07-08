package com.example.myapplication_2.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication_2.data.FridgeRepository
import com.example.myapplication_2.databinding.FragmentHomeBinding
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.ui.model.Recipe

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var fullRecipeList: List<Recipe>
    private var recommendedRecipe: Recipe? = null  // 추천 메뉴 저장 변수

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 키보드 자동 열림 방지
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )

        // 전체 레시피 목록
        fullRecipeList = RecipeRepository.getAllRecipes()

        // 추천 레시피는 한 번만 설정
        if (recommendedRecipe == null) {
            recommendedRecipe = fullRecipeList.randomOrNull()
        }

        setupRecipeRecyclerView(fullRecipeList, recommendedRecipe)
        setupSearchBar()

        // 완료 버튼 누르면 키보드 닫기
        binding.searchBar.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(v)
                binding.searchBar.clearFocus()
                true
            } else false
        }

        // 바깥 터치하면 키보드 닫기
        binding.root.setOnTouchListener { v, _ ->
            hideKeyboard(v)
            binding.searchBar.clearFocus()
            false
        }

        // 냉장고 버튼 클릭 시 필터링
        binding.fridgeButton.setOnClickListener {
            val fridgeItems = FridgeRepository.getFridge().toList()

            val filtered = if (fridgeItems.isEmpty()) {
                fullRecipeList
            } else {
                fullRecipeList
                    .map { recipe ->
                        val matchCount = fridgeItems.count { keyword ->
                            recipe.title.contains(keyword, ignoreCase = true) ||
                                    recipe.author.contains(keyword, ignoreCase = true) ||
                                    recipe.ingredients.any { it.contains(keyword, ignoreCase = true) }
                        }
                        recipe to matchCount
                    }
                    .filter { it.second > 0 }
                    .sortedByDescending { it.second }
                    .map { it.first }
            }

            val recommended = if (fridgeItems.isEmpty()) recommendedRecipe else null

            setupRecipeRecyclerView(filtered, recommended)

            // 검색창에도 표시
            binding.searchBar.setText(fridgeItems.joinToString(", "))
        }

        return binding.root
    }

    private fun setupRecipeRecyclerView(recipeList: List<Recipe>, recommended: Recipe?) {
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.adapter =
            RecipeAdapter(recipeList, recommended, showSectionHeader = recommended != null)
    }

    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()

                val keywords = query.split(',', ' ')
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                val filtered = if (keywords.isEmpty()) {
                    fullRecipeList
                } else {
                    fullRecipeList
                        .map { recipe ->
                            val matchCount = keywords.count { keyword ->
                                recipe.title.contains(keyword, ignoreCase = true) ||
                                        recipe.author.contains(keyword, ignoreCase = true) ||
                                        recipe.ingredients.any { it.contains(keyword, ignoreCase = true) }
                            }
                            recipe to matchCount
                        }
                        .filter { it.second > 0 }
                        .sortedByDescending { it.second }
                        .map { it.first }
                }

                val recommended = if (keywords.isEmpty()) recommendedRecipe else null

                setupRecipeRecyclerView(filtered, recommended)
            }
        })
    }

    private fun hideKeyboard(view: View) {
        val imm = requireContext().getSystemService(InputMethodManager::class.java)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

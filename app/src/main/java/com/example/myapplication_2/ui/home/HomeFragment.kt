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
import com.example.myapplication_2.databinding.FragmentHomeBinding
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.data.sampleRecipes
import com.example.myapplication_2.ui.model.Recipe

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var fullRecipeList: List<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 키보드 자동 열림 방지
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )

        // 샘플 레시피 초기화
        if (RecipeRepository.recipeList.isEmpty()) {
            RecipeRepository.recipeList.addAll(sampleRecipes)
        }
        fullRecipeList = RecipeRepository.recipeList

        setupRecipeRecyclerView(fullRecipeList, fullRecipeList.randomOrNull())
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

        return binding.root
    }

    private fun setupRecipeRecyclerView(recipeList: List<Recipe>, recommendedRecipe: Recipe?) {
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.adapter = RecipeAdapter(recipeList, recommendedRecipe, showSectionHeader = recommendedRecipe != null)
    }

    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()

                val filtered = if (query.isBlank()) {
                    fullRecipeList
                } else {
                    fullRecipeList.filter { recipe ->
                        recipe.title.contains(query, ignoreCase = true) ||
                                recipe.author.contains(query, ignoreCase = true) ||
                                recipe.ingredients.any { it.contains(query, ignoreCase = true) }
                    }
                }

                val recommended = if (query.isBlank()) filtered.randomOrNull() else null

                // 🔹 RecyclerView 갱신
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

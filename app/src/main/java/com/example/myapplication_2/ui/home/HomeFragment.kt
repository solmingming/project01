package com.example.myapplication_2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication_2.databinding.FragmentHomeBinding
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.data.sampleRecipes
import com.example.myapplication_2.R
import com.example.myapplication_2.ui.home.RecipeAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // RecyclerView 세팅
        setupRecipeRecyclerView()

        return binding.root
    }

    // onViewCreated에서 추천 카드 세팅
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // sampleRecipes를 RecipeRepository에 추가
        if (RecipeRepository.recipeList.isEmpty()) {
            RecipeRepository.recipeList.addAll(sampleRecipes)
        }

        setupRecommendCard() // 추천 메뉴 카드 세팅
    }

    // 🔹 추천 카드 설정
    private fun setupRecommendCard() {
        val recommendTitle = binding.recommendTitle
        val recommendImage = binding.recommendImage
        val recommendTags = binding.recommendTags
        val stars = arrayOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)

        recommendTitle.text = "오늘의 넙죽메뉴"

        // RecipeList가 비어 있지 않으면 랜덤으로 선택
        if (RecipeRepository.recipeList.isNotEmpty()) {
            val recommendedRecipe = RecipeRepository.recipeList.random()

            recommendedRecipe.let { recipe ->
                // 이미지 경로 설정
                val imagePath = "file:///android_asset/dishImage/${recipe.imageFileName}"

                // Glide로 이미지 로딩
                Glide.with(this)
                    .load(imagePath)
                    .into(recommendImage)

                // 레시피 태그 설정
                recommendTags.text = "#${recipe.ingredients.joinToString(" #")}"

                // 별점 설정 (예시로 4개 별점 설정)
                val rating = recipe.rating
                for (i in stars.indices) {
                    stars[i].setImageResource(
                        if (i < rating) R.drawable.yellow_star2 else R.drawable.gray_star2
                    )
                }
            }
        } else {
            // 리스트가 비어 있을 경우 기본 이미지 표시
            recommendTitle.text = "레시피가 없습니다."

        }
    }

    // 🔸 RecyclerView 설정
    private fun setupRecipeRecyclerView() {
        val recipeList = RecipeRepository.recipeList
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.adapter = RecipeAdapter(recipeList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

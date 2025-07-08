package com.example.myapplication_2.ui.notifications

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.data.sampleRecipes
import com.example.myapplication_2.databinding.FragmentNotificationsBinding
import com.example.myapplication_2.ui.notifications.GridRecipeAdapter
import com.example.myapplication_2.ui.model.Recipe
import com.example.myapplication_2.utils.UserGenerator

class NotificationsFragment : Fragment() {

    val user = UserGenerator.generate()
    val userName = user.name

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: GridRecipeAdapter
    private val recipeList = mutableListOf<Recipe>()

    // 형용사와 재료 조합 후보
    private val adjectives = listOf("달콤한", "매콤한", "바삭한", "촉촉한")
    private val ingredients = listOf("피자", "떡볶이", "삼겹살", "라면")

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
        val user = UserGenerator.generate()
        val userName = getOrCreateRandomUserName(context)

        // 프로필 이미지 및 컬러 적용
        binding.profileImage.setImageResource(user.imageResId)
        binding.profileImage.backgroundTintList = ColorStateList.valueOf(Color.parseColor(user.colorHex))

        // 유저 이름 표시
        binding.textUsername.text = "@$userName"

        // 레시피 데이터 준비
        if (RecipeRepository.getAllRecipes().isEmpty()) {
            RecipeRepository.addAll(sampleRecipes)
        }

        setupRecyclerView(userName)
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

    private fun getOrCreateRandomUserName(context: Context): String {
        val prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val existingName = prefs.getString("randomUserName", null)
        if (existingName != null) return existingName

        val randomName = "${adjectives.random()} ${ingredients.random()}"
        prefs.edit().putString("randomUserName", randomName).apply()
        return randomName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

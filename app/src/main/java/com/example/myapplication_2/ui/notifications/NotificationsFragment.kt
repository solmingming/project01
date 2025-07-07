package com.example.myapplication_2.ui.notifications

<<<<<<< HEAD
import android.graphics.Color
=======
import android.content.Context
>>>>>>> origin/07072100
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.databinding.FragmentNotificationsBinding
import com.example.myapplication_2.ui.notifications.GridRecipeAdapter
import com.example.myapplication_2.ui.model.Recipe
<<<<<<< HEAD
import com.example.myapplication_2.utils.UserGenerator
import android.content.res.ColorStateList
=======
import com.example.myapplication_2.data.sampleRecipes
>>>>>>> origin/07072100

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

<<<<<<< HEAD
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList = mutableListOf<Recipe>() // 나중에 실제 데이터로 대체
=======
    private lateinit var recipeAdapter: GridRecipeAdapter
    private val recipeList = mutableListOf<Recipe>()

    // 형용사와 재료 조합 후보
    private val adjectives = listOf("달콤한")
    private val ingredients = listOf("피자")
>>>>>>> origin/07072100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
<<<<<<< HEAD
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ 유저 정보 랜덤 생성 및 뷰에 반영
        val user = UserGenerator.generate()

        binding.userNameTextView.text = user.name
        binding.profileImageView.setImageResource(user.imageResId)

        val backgroundView = binding.colorCircle
        backgroundView.background.setTint(Color.parseColor(user.colorHex))


// 동그라미 배경 색상 적용
        val circleView = binding.colorCircle
        circleView.backgroundTintList = ColorStateList.valueOf(Color.parseColor(user.colorHex))

        binding.userNameTextView.text = user.name
        binding.profileImageView.setImageResource(user.imageResId)

        // ✅ 레시피 그리드 설정
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipeAdapter(recipeList, null, false)
=======

        val context = requireContext()
        val userName = getOrCreateRandomUserName(context)  // 예: "달콤한 피자"
        binding.textUsername.text = "@$userName"           // 이건 TextView 전용으로 '@' 추가

        if(RecipeRepository.getAllRecipes().isEmpty()){
            RecipeRepository.addAll(sampleRecipes)
        }
        setupRecyclerView(userName)

        return binding.root
    }

    private fun setupRecyclerView(userName: String) {
        val allRecipes = RecipeRepository.getAllRecipes()
        val userRecipes = allRecipes.filter { it.author == userName }  // ← '@' 없이 비교
>>>>>>> origin/07072100

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

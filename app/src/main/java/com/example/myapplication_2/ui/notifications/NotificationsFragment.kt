package com.example.myapplication_2.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication_2.databinding.FragmentNotificationsBinding
import com.example.myapplication_2.ui.RecipeAdapter
import com.example.myapplication_2.ui.model.Recipe
import com.example.myapplication_2.utils.UserGenerator
import android.content.res.ColorStateList

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList = mutableListOf<Recipe>() // 나중에 실제 데이터로 대체

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

        binding.recipeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = recipeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

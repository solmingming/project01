package com.example.myapplication_2.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_2.ui.model.Recipe
import com.example.myapplication_2.R
import com.example.myapplication_2.databinding.FragmentNotificationsBinding
import com.example.myapplication_2.ui.RecipeAdapter

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList = mutableListOf<Recipe>()  // 나중에 데이터 전달 또는 공유

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root = binding.root

        // RecyclerView 설정
        recipeAdapter = RecipeAdapter(recipeList)
        binding.recipeRecyclerView.layoutManager = GridLayoutManager(context, 3)
        binding.recipeRecyclerView.adapter = recipeAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.myapplication_2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication_2.databinding.FragmentHomeBinding
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.ui.home.RecipeAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // 🔥 레시피 목록은 Repository에서 가져오기
        val recipeList = RecipeRepository.recipeList

        // 🔧 RecyclerView 설정
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipeRecyclerView.adapter = RecipeAdapter(recipeList)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

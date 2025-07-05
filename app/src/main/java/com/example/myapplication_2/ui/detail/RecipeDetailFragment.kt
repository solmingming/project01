package com.example.myapplication_2.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication_2.databinding.FragmentRecipeDetailBinding
import com.example.myapplication_2.data.RecipeRepository

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val index = arguments?.getInt("recipe_index") ?: 0
        val recipe = RecipeRepository.recipeList.getOrNull(index)

        recipe?.let {
            binding.recipeTitle.text = it.title
            binding.recipeAuthor.text = "by ${it.author}"
            binding.recipeDescription.text = it.description

            val imagePath = "file:///android_asset/dishImage/${it.imageFileName}"
            Glide.with(requireContext())
                .load(imagePath)
                .into(binding.recipeImage)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

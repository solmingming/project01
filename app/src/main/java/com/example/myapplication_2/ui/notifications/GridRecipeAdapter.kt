package com.example.myapplication_2.ui.notifications

import android.content.Context
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication_2.R
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.ui.model.Recipe

class GridRecipeAdapter(
    private val recipeList: List<Recipe>
) : RecyclerView.Adapter<GridRecipeAdapter.GridViewHolder>() {

    class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView = view.findViewById(R.id.gridImage)

        init {
            recipeImage.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(0, 0, view.width, view.height, 16f)
                }
            }
            recipeImage.clipToOutline = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe_grid, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val recipe = recipeList[position]
        val context: Context = holder.itemView.context

        val uri = recipe.imageUri?.let { Uri.parse(it)}

        if (uri != null) {
            Glide.with(context)
                .load(uri)  // ✅ Uri 그대로 사용
                .into(holder.recipeImage)
        } else {
            val assetPath = "file:///android_asset/dishImage/${recipe.imageFileName}"
            Glide.with(context)
                .load(assetPath)
                .into(holder.recipeImage)
        }

        holder.recipeImage.setOnClickListener {
            val bundle = Bundle().apply {
                val indexInRepo = RecipeRepository.getAllRecipes().indexOf(recipe)
                putInt("recipe_index", indexInRepo.takeIf { it >= 0 } ?: 0)
            }

            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_navigation_notifications_to_recipeDetailFragment, bundle)
        }
    }


    override fun getItemCount(): Int = recipeList.size
}

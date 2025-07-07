package com.example.myapplication_2.ui

import com.example.myapplication_2.ui.model.Recipe
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.myapplication_2.R
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater
import android.net.Uri




class RecipeAdapter(private val recipes: List<Recipe>, nothing: Nothing?, bool: Boolean) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.recipeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe_grid, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.imageView.setImageURI(Uri.parse(recipe.imageFileName))
    }

    override fun getItemCount(): Int = recipes.size
}

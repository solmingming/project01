package com.example.myapplication_2.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_2.R
import com.example.myapplication_2.ui.model.Recipe
import java.io.IOException
import android.os.Bundle
import androidx.navigation.Navigation

class RecipeAdapter(private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.textTitle)
        val ingredientsTextView: TextView = view.findViewById(R.id.textIngredients)
        val difficultyContainer: LinearLayout = view.findViewById(R.id.difficultyContainer)
        val cardContent: LinearLayout = view.findViewById(R.id.cardContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipeList[position]

        // 제목 및 재료 표시
        holder.titleTextView.text = recipe.title
        holder.ingredientsTextView.text = recipe.ingredients.joinToString(" ") { "#$it" }

        // 이미지 로드 (assets/dishImage/파일명)
        val context: Context = holder.itemView.context
        try {
            val assetManager = context.assets
            val inputStream = assetManager.open("dishImage/${recipe.imageFileName}")
            val drawable = android.graphics.drawable.Drawable.createFromStream(inputStream, null)
            holder.imageView.setImageDrawable(drawable)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 난이도(물방울) 초기화 후 다시 채움
        holder.difficultyContainer.removeAllViews()
        val difficulty = recipe.rating.coerceIn(0, 5)

        repeat(difficulty) {
            val dropIcon = ImageView(context).apply {
                setImageResource(R.drawable.ic_drop) // drawable에 있는 물방울 이미지 리소스
                val sizeInDp = 25
                val scale = context.resources.displayMetrics.density
                val sizeInPx = (sizeInDp * scale + 0.5f).toInt()
                layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx).apply {
                    setMargins(1, 0, 1, 0)
                }
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
            holder.difficultyContainer.addView(dropIcon)
        }

        holder.cardContent.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("recipe_index", position)
            }
            Navigation.findNavController(holder.itemView)
                .navigate(R.id.action_navigation_home_to_recipeDetailFragment, bundle)
        }
    }
}

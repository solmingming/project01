package com.example.myapplication_2.ui.home

import android.content.Context
import android.graphics.Outline
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication_2.R
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.ui.model.Recipe
import java.io.IOException

class RecipeAdapter(
    private val recipeList: List<Recipe>,
    private val recommendedRecipe: Recipe?,
    private val showSectionHeader: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_RECOMMEND_HEADER = 0
        private const val VIEW_TYPE_RECOMMEND = 1
        private const val VIEW_TYPE_SECTION_HEADER = 2
        private const val VIEW_TYPE_RECIPE = 3
    }

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.textTitle)
        val ingredientsTextView: TextView = view.findViewById(R.id.textIngredients)
        val difficultyContainer: LinearLayout = view.findViewById(R.id.difficultyContainer)
        val cardContent: LinearLayout = view.findViewById(R.id.cardContent)
    }

    class RecommendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recommendImage: ImageView = view.findViewById(R.id.recommendImage)
        val recommendTitle: TextView = view.findViewById(R.id.recommendTitle)
        val recommendTags: TextView = view.findViewById(R.id.recommendTags)
        val starViews: List<ImageView> = listOf(
            view.findViewById(R.id.star1),
            view.findViewById(R.id.star2),
            view.findViewById(R.id.star3),
            view.findViewById(R.id.star4),
            view.findViewById(R.id.star5)
        )
    }

    class SectionHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sectionTitle: TextView = view.findViewById(R.id.sectionTitle)
    }

    override fun getItemCount(): Int {
        var count = recipeList.size
        if (recommendedRecipe != null) count += 2 // 추천 헤더 + 추천 카드
        if (showSectionHeader) count += 1         // 넙죽 레시피 섹션 헤더
        return count
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            recommendedRecipe != null && position == 0 -> VIEW_TYPE_RECOMMEND_HEADER
            recommendedRecipe != null && position == 1 -> VIEW_TYPE_RECOMMEND
            (recommendedRecipe != null && showSectionHeader && position == 2) ||
                    (recommendedRecipe == null && showSectionHeader && position == 0) -> VIEW_TYPE_SECTION_HEADER
            else -> VIEW_TYPE_RECIPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_RECOMMEND_HEADER -> SectionHeaderViewHolder(inflater.inflate(R.layout.item_section_header, parent, false)).apply {
                itemView.findViewById<TextView>(R.id.sectionTitle).text = "오늘의 넙죽메뉴"
            }
            VIEW_TYPE_RECOMMEND -> RecommendViewHolder(inflater.inflate(R.layout.item_recommend, parent, false))
            VIEW_TYPE_SECTION_HEADER -> SectionHeaderViewHolder(inflater.inflate(R.layout.item_section_header, parent, false)).apply {
                itemView.findViewById<TextView>(R.id.sectionTitle).text = "넙죽 레시피"
            }
            else -> RecipeViewHolder(inflater.inflate(R.layout.item_recipe, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val offset = when {
            recommendedRecipe != null && showSectionHeader -> 3
            recommendedRecipe != null && !showSectionHeader -> 2
            recommendedRecipe == null && showSectionHeader -> 1
            else -> 0
        }

        when (holder) {
            is RecommendViewHolder -> {
                recommendedRecipe?.let { recipe ->
                    holder.recommendTitle.text = recipe.title
                    holder.recommendTags.text = "#${recipe.ingredients.joinToString(" #")}"

                    val context = holder.itemView.context
                    try {
                        val inputStream = context.assets.open("dishImage/${recipe.imageFileName}")
                        val drawable = android.graphics.drawable.Drawable.createFromStream(inputStream, null)
                        holder.recommendImage.setImageDrawable(drawable)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    holder.starViews.forEachIndexed { index, starView ->
                        starView.setImageResource(
                            if (index < recipe.rating) R.drawable.yellow_star2
                            else R.drawable.gray_star2
                        )
                    }

                    holder.recommendImage.outlineProvider = object : ViewOutlineProvider() {
                        override fun getOutline(view: View, outline: Outline) {
                            outline.setRoundRect(0, 0, view.width, view.height, 24f)
                        }
                    }
                    holder.recommendImage.clipToOutline = true

                    val navigateToDetail: (View) -> Unit = {
                        val bundle = Bundle().apply {
                            val recipeIndex = recipeList.indexOfFirst { it.imageFileName == recipe.imageFileName }
                            putInt("recipe_index", recipeIndex.takeIf { it >= 0 } ?: 0)
                        }
                        Navigation.findNavController(holder.itemView)
                            .navigate(R.id.action_navigation_home_to_recipeDetailFragment, bundle)
                    }

                    holder.recommendImage.setOnClickListener(navigateToDetail)
                    holder.recommendTitle.setOnClickListener(navigateToDetail)
                }
            }

            is RecipeViewHolder -> {
                val recipe = recipeList[position - offset]
                holder.titleTextView.text = recipe.title
                holder.ingredientsTextView.text = recipe.ingredients.joinToString(" ") { "#$it" }

                val context: Context = holder.itemView.context
                try {
                    val inputStream = context.assets.open("dishImage/${recipe.imageFileName}")
                    val drawable = android.graphics.drawable.Drawable.createFromStream(inputStream, null)
                    holder.imageView.setImageDrawable(drawable)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                holder.difficultyContainer.removeAllViews()
                val difficulty = recipe.rating.coerceIn(0, 5)
                repeat(difficulty) {
                    val starIcon = ImageView(context).apply {
                        setImageResource(R.drawable.yellow_star2)
                        val sizeInDp = 15
                        val scale = context.resources.displayMetrics.density
                        val sizeInPx = (sizeInDp * scale + 0.5f).toInt()
                        layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx).apply {
                            setMargins(6, 0, 1, 0)
                        }
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                    holder.difficultyContainer.addView(starIcon)
                }

                holder.imageView.outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View, outline: Outline) {
                        outline.setRoundRect(0, 0, view.width, view.height, 24f)
                    }
                }
                holder.imageView.clipToOutline = true

                holder.cardContent.setOnClickListener {
                    val bundle = Bundle().apply {
                        val indexInRepo = RecipeRepository.recipeList.indexOfFirst { it.imageFileName == recipe.imageFileName }
                        putInt("recipe_index", indexInRepo.takeIf { it >= 0 } ?: 0)
                    }
                    Navigation.findNavController(holder.itemView)
                        .navigate(R.id.action_navigation_home_to_recipeDetailFragment, bundle)
                }
            }
        }
    }
}

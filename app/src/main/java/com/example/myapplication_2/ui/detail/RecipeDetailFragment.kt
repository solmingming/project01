package com.example.myapplication_2.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication_2.R
import com.example.myapplication_2.data.RecipeRepository
import com.example.myapplication_2.databinding.FragmentRecipeDetailBinding
import android.view.Gravity
import android.graphics.Outline
import android.view.ContextThemeWrapper
import android.view.ViewOutlineProvider
import com.google.android.material.chip.Chip

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private var selectedStepIndex = -1 // 클릭된 step 인덱스 기억용

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        val index = arguments?.getInt("recipe_index") ?: 0
        val recipe = RecipeRepository.recipeList.getOrNull(index)

        recipe?.let { rcp ->
            val context = requireContext()

            // 제목 & 작성자
            binding.recipeTitle.text = rcp.title
            binding.recipeAuthor.apply {
                text = rcp.author
                paint.isUnderlineText = true
            }

            // 이미지 로딩 (정방형)
            val assetPath = "file:///android_asset/dishImage/${rcp.imageFileName}"
            Glide.with(this)
                .load(assetPath)
                .into(binding.recipeImage)

            // 이미지 둥글게 만들기 (OutlineProvider)
            binding.recipeImage.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val radius = 24f // 둥근 정도 설정
                    outline.setRoundRect(0, 0, view.width, view.height, radius)
                }
            }
            binding.recipeImage.clipToOutline = true // 클리핑을 true로 설정하여 모서리를 둥글게 만듦


            // 난이도 (별점)
            binding.ratingContainer.removeAllViews()
            val rating = rcp.rating.coerceIn(0, 5)
            repeat(5) { i ->
                val star = ImageView(context)
                val drawableId =
                    if (i < rating) R.drawable.yellow_star2 else R.drawable.gray_star2
                star.setImageResource(drawableId)

                val size = (20 * resources.displayMetrics.density).toInt()
                val margin = (2 * resources.displayMetrics.density).toInt()
                val params = ViewGroup.MarginLayoutParams(size, size)
                params.setMargins(margin, 0, margin, 0)
                star.layoutParams = params

                binding.ratingContainer.addView(star)
            }

            // 재료 태그
            binding.ingredientsContainer.removeAllViews()
            rcp.ingredients.forEach { ingredient ->
                val tag = TextView(context).apply {
                    text = "#$ingredient"
                    textSize = 10f
                    setPadding(16, 8, 16, 8)
                    setTextColor(ContextCompat.getColor(context, R.color.purple_200))
                    background =
                        ContextCompat.getDrawable(context, R.drawable.ingredient_tag_background)
                    elevation = 6f
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(8, 8, 8, 8)
                    }
                }
                binding.ingredientsContainer.addView(tag)
            }



            // 레시피 단계
            binding.textDescriptionContainer.removeAllViews()
            rcp.description.forEachIndexed { idx, stepText ->
                val stepLayout = LinearLayout(context).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 12, 0, 14)
                    }
                }

                val stepBox = TextView(context).apply {
                    text = "step ${idx + 1}"
                    setPadding(24, 8, 24, 8)
                    setTextColor(ContextCompat.getColor(context, android.R.color.white))
                    background = ContextCompat.getDrawable(context, R.drawable.gray_round_box)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 24, 0)
                    }

                    setOnClickListener {
                        selectedStepIndex = idx // index는 클릭한 step의 인덱스
                        updateStepColors(rcp.description.size)
                    }

                    tag = idx // 나중에 색 바꿀 때 index용
                }

                val stepTextView = TextView(context).apply {
                    text = stepText
                    textSize = 13.5f
                    setTextColor(ContextCompat.getColor(context, R.color.gray_kim))
                    layoutParams = LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                }

                stepLayout.addView(stepBox)
                stepLayout.addView(stepTextView)

                binding.textDescriptionContainer.addView(stepLayout)
            }
            selectedStepIndex = 0
            updateStepColors(rcp.description.size)

        }

        return binding.root
    }

    private fun updateStepColors(totalSteps: Int) {
        for (i in 0 until totalSteps) {
            val stepLayout = binding.textDescriptionContainer.getChildAt(i) as LinearLayout
            val stepBox = stepLayout.getChildAt(0) as TextView

            val bgRes = if (i <= selectedStepIndex) {
                R.drawable.blue_round_box
            } else {
                R.drawable.gray_round_box
            }
            stepBox.background = ContextCompat.getDrawable(requireContext(), bgRes)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

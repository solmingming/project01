// com.example.myapplication_2.data.RecipeRepository.kt

package com.example.myapplication_2.data

import com.example.myapplication_2.ui.model.Recipe

object RecipeRepository {
    private val recipeList = mutableListOf<Recipe>()

    init {
        if (recipeList.isEmpty()) {
            recipeList.addAll(sampleRecipes)
        }
    }

    fun getAllRecipes(): List<Recipe> = recipeList

    fun addAll(recipes: List<Recipe>) {
        recipeList.addAll(recipes)
    }

    fun getRecipe(index: Int): Recipe? = recipeList.getOrNull(index)

    fun addRecipe(recipe: Recipe) {
        recipeList.add(recipe)
    }
}


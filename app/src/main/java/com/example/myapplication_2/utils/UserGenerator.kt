package com.example.myapplication_2.utils

import com.example.myapplication_2.R

data class RandomUser(
    val name: String,
    val colorHex: String,
    val imageResId: Int
)

object UserGenerator {

    private var cachedUser: RandomUser? = null

    private val adjectiveColorMap = mapOf(
        "달콤한" to "#FFE6E6"
    )

    private val ingredientImageMap = mapOf(
        "피자" to R.drawable.pizza
    )

    fun generate(): RandomUser {
        if (cachedUser != null) return cachedUser!!

        val adjective = adjectiveColorMap.keys.random()
        val ingredient = ingredientImageMap.keys.random()
        val color = adjectiveColorMap[adjective] ?: "#FFFFFF"
        val imageResId = ingredientImageMap[ingredient] ?: R.drawable.default_avatar

        cachedUser = RandomUser("$adjective $ingredient", color, imageResId)
        return cachedUser!!
    }

    fun generateRandomUsername(): String {
        val adjective = adjectiveColorMap.keys.random()
        val ingredient = ingredientImageMap.keys.random()
        return "$adjective $ingredient"
    }
}

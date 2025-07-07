package com.example.myapplication_2.utils

import com.example.myapplication_2.R

data class RandomUser(
    val name: String,
    val colorHex: String,
    val imageResId: Int
)

object UserGenerator {

    private val adjectiveColorMap = mapOf(
        "달콤한" to "#FFE6E6",
        "바삭한" to "#FFF4E6",
        "매콤한" to "#FF7E7E",
        "따뜻한" to "#FFD3B7",
        "촉촉한" to "#CEE3D5",
        "담백한" to "#FFFEBB",
        "고소한" to "#FFCB78",
        "쫄깃한" to "#FFE0FB",
        "시원한" to "#BED7FF",
        "향긋한" to "#E0FFD8"
    )

    private val ingredientImageMap = mapOf(
        "계란" to R.drawable.egg,
        "브로콜리" to R.drawable.broccoli,
        "딸기" to R.drawable.strawberry,
        "키위" to R.drawable.kiwi,
        "토마토" to R.drawable.tomato,
        "쿠키" to R.drawable.cookie,
        "식빵" to R.drawable.bread,
        "아보카도" to R.drawable.avocado,
        "바나나" to R.drawable.banana,
        "피자" to R.drawable.pizza,
        "스마일 감자" to R.drawable.smile_potato,
        "소금빵" to R.drawable.salt_bread,
        "생선" to R.drawable.fish,
        "고기" to R.drawable.meat
    )

    fun generate(): RandomUser {
        val adjective = adjectiveColorMap.keys.random()
        val ingredient = ingredientImageMap.keys.random()
        val color = adjectiveColorMap[adjective] ?: "#FFFFFF"
        val imageResId = ingredientImageMap[ingredient] ?: R.drawable.default_avatar

        return RandomUser("$adjective $ingredient", color, imageResId)
    }

    // 이건 따로 빼도 되지만, 여기에 간단히 유지해도 됨
    fun generateRandomUsername(): String {
        val adjective = adjectiveColorMap.keys.random()
        val ingredient = ingredientImageMap.keys.random()
        return "$adjective $ingredient"
    }
}

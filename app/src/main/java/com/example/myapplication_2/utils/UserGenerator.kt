package com.example.myapplication_2.utils

import android.content.Context
import android.graphics.Color
import com.example.myapplication_2.R

data class RandomUser(
    val name: String,
    val colorHex: String,
    val imageResId: Int
)

object UserGenerator {

    private var cachedUser: RandomUser? = null

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
        "피자" to R.drawable.pizza,
        "계란" to R.drawable.egg,
        "브로콜리" to R.drawable.broccoli,
        "딸기" to R.drawable.strawberry,
        "키위" to R.drawable.kiwi,
        "토마토" to R.drawable.tomato,
        "쿠키" to R.drawable.cookie,
        "식빵" to R.drawable.bread,
        "아보카도" to R.drawable.avocado,
        "바나나" to R.drawable.banana,
        "스마일 감자" to R.drawable.smile_potato,
        "소금빵" to R.drawable.salt_bread,
        "생선" to R.drawable.fish,
        "고기" to R.drawable.meat
    )

    private const val PREF_NAME = "UserPrefs"
    private const val KEY_NAME = "username"
    private const val KEY_COLOR = "userColor"
    private const val KEY_IMAGE = "userImage"

    fun generateFixedFirstUser(): RandomUser {
        val adjective = adjectiveColorMap.keys.first() // "달콤한"
        val ingredient = ingredientImageMap.keys.first() // "피자"
        val color = adjectiveColorMap[adjective] ?: "#FFFFFF"
        val imageResId = ingredientImageMap[ingredient] ?: R.drawable.default_avatar
        return RandomUser("$adjective $ingredient", color, imageResId)
    }

    fun generateNewRandomUser(): RandomUser {
        val adjective = adjectiveColorMap.keys.random()
        val ingredient = ingredientImageMap.keys.random()
        val color = adjectiveColorMap[adjective] ?: "#FFFFFF"
        val imageResId = ingredientImageMap[ingredient] ?: R.drawable.default_avatar
        return RandomUser("$adjective $ingredient", color, imageResId)
    }

    fun saveUserToPrefs(context: Context, user: RandomUser) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_NAME, user.name)
            .putString(KEY_COLOR, user.colorHex)
            .putInt(KEY_IMAGE, user.imageResId)
            .apply()

        cachedUser = user
        android.util.Log.d("UserGenerator", "✅ User saved to prefs: $user")
    }

    fun loadUserFromPrefs(context: Context): RandomUser? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val name = prefs.getString(KEY_NAME, null)
        val color = prefs.getString(KEY_COLOR, null)
        val imageResId = prefs.getInt(KEY_IMAGE, -1)

        val loaded = if (name != null && color != null && imageResId != -1) {
            RandomUser(name, color, imageResId).also {
                cachedUser = it
            }
        } else null

        android.util.Log.d("UserGenerator", "📦 Loaded user from prefs: $loaded")
        return loaded
    }

    fun setCachedUser(user: RandomUser) {
        cachedUser = user
    }

    fun getCachedUser(): RandomUser {
        return cachedUser ?: throw IllegalStateException("User not cached. Load or create first.")
    }

    // ✅ 추가: 형용사에 대응하는 색상을 int 값으로 반환하는 함수
    fun getColorInt(context: Context, adjective: String): Int {
        val hex = adjectiveColorMap[adjective] ?: "#FFFFFF"
        return Color.parseColor(hex)
    }
}

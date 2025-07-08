package com.example.myapplication_2.utils

import android.content.Context
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
        "바삭한" to "#FFF4E6"
    )

    private val ingredientImageMap = mapOf(
        "피자" to R.drawable.pizza,
        "쿠키" to R.drawable.cookie
    )

    private const val PREF_NAME = "UserPrefs"
    private const val KEY_NAME = "username"
    private const val KEY_COLOR = "userColor"
    private const val KEY_IMAGE = "userImage"

    /**
     * 랜덤 유저 생성 (캐시 및 저장은 외부에서 처리)
     */
    // 처음 한 번만 호출될 함수
    fun generateFixedFirstUser(): RandomUser {
        val adjective = adjectiveColorMap.keys.first() // "달콤한"
        val ingredient = ingredientImageMap.keys.first() // "피자"
        val color = adjectiveColorMap[adjective] ?: "#FFFFFF"
        val imageResId = ingredientImageMap[ingredient] ?: R.drawable.default_avatar
        return RandomUser("$adjective $ingredient", color, imageResId)
    }

    // 랜덤 유저는 그대로 유지
    fun generateNewRandomUser(): RandomUser {
        val adjective = adjectiveColorMap.keys.random()
        val ingredient = ingredientImageMap.keys.random()
        val color = adjectiveColorMap[adjective] ?: "#FFFFFF"
        val imageResId = ingredientImageMap[ingredient] ?: R.drawable.default_avatar
        return RandomUser("$adjective $ingredient", color, imageResId)
    }


    /**
     * SharedPreferences → 유저 정보 불러오기
     * (MainActivity 전용 사용)
     */
    fun saveUserToPrefs(context: Context, user: RandomUser) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_NAME, user.name)
            .putString(KEY_COLOR, user.colorHex)
            .putInt(KEY_IMAGE, user.imageResId)
            .apply()

        cachedUser = user

        // 디버깅용 로그 추가
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

        // 디버깅용 로그
        android.util.Log.d("UserGenerator", "📦 Loaded user from prefs: $loaded")
        return loaded
    }


    /**
     * 메모리 캐시에 유저 수동 설정
     */
    fun setCachedUser(user: RandomUser) {
        cachedUser = user
    }

    /**
     * 메모리 캐시 유저 가져오기
     */
    fun getCachedUser(): RandomUser {
        return cachedUser ?: throw IllegalStateException("User not cached. Load or create first.")
    }
}

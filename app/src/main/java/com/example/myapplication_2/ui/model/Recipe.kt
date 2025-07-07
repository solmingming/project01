package com.example.myapplication_2.ui.model

import android.net.Uri


data class Recipe(
    val imageFileName: String,
    val title: String,
    val description: List<String>,   // ✅ 이 부분이 중요!
    val rating: Int,
    val author: String,
    val ingredients: List<String>
)
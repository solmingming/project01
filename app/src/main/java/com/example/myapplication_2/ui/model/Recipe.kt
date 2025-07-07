package com.example.myapplication_2.ui.model

import android.net.Uri


data class Recipe(
    val imageFileName: String,   // 예: "pasta1.jpg"
    val imageUri: String? = null,
    val title: String,           // 음식 제목
    val description: List<String> = listOf(),     // 레시피 본문
    val rating: Int,             // 별점 (0~5 사이 정수)
    val author: String,           // 만든이
    val ingredients: List<String> = listOf()
)
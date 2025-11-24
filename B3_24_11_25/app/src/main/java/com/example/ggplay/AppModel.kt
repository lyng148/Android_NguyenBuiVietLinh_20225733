package com.example.ggplay

data class AppModel(
    val name: String,
    val category: String,
    val rating: String,
    val imageUrl: Int // Dùng resource ID (R.drawable.xxx) hoặc màu sắc tạm
)
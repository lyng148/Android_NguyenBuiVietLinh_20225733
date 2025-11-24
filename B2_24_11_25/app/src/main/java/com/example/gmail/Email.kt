package com.example.gmail

data class Email(
    val name: String,
    val subject: String,
    val content: String,
    val time: String,
    val color: Int // Màu nền của Avatar (Xanh, Đỏ, Tím...)
)
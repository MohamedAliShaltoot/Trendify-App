package com.example.trendify.data.model

data class DataXXXXXXXX(
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val images: List<String>,
    var in_cart: Boolean,
    val in_favorites: Boolean,
    val name: String,
    val old_price: Int,
    val price: Int
)
package com.example.trendify.data.model

data class DataXXXXXX(
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val images: List<String>,
    var in_cart: Boolean,
    var in_favorites: Boolean,
    val name: String,
    val old_price: Double,
    val price: Double
)
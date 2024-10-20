package com.example.market.data.model

data class Product(
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
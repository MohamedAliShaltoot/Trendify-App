package com.example.trendify.data.model

import com.example.trendify.data.model.Data

data class RegisterResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)
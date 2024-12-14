package com.example.e_commerce_kot.data


data class Product(
    val name: String,
    val description: String,
    val price: String,
    var isLiked: Boolean,
    val image: Int // Resource ID for image
)

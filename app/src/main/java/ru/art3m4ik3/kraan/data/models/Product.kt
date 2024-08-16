package ru.art3m4ik3.kraan.data.models

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val characteristics: String,
    val image: String,
    val price: Double
)
package com.app.quickmart.models

data class ProductModel(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val mrp: String = "",
    val price: String = "",
    val category: String = "",
    val images: List<String> = emptyList()
)

package com.app.quickmart.models

data class UserModel(
    val name: String = "",
    val email: String = "",
    val uid: String = "",
    val cartItems: Map<String, Long> = emptyMap(),
    val address: String = ""
)

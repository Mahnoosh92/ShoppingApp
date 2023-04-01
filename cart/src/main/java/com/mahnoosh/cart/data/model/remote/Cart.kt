package com.mahnoosh.cart.data.model.remote

data class Cart(
    val date: String?,
    val id: Int?,
    val products: List<CartProduct>?,
    val userId: Int?
)

data class CartProduct(
    val productId: Int?, val quantity: Int?
)

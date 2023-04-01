package com.mahnoosh.cart.presentation

import com.mahnoosh.core.data.models.local.Product

sealed class CartState {
    data class Carts(val products: List<Product>) : CartState()
    data class Error(val error: String) : CartState()
    object Loading : CartState()
}

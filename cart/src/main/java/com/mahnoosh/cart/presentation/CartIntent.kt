package com.mahnoosh.cart.presentation

sealed class CartIntent {
    object GetCarts : CartIntent()
}

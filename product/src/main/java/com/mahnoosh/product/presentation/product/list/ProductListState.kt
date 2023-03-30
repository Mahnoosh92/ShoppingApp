package com.mahnoosh.product.presentation.product.list

import com.mahnoosh.core.data.models.local.Product

sealed class ProductListState {
    object Loading : ProductListState()
    data class Products(val products: List<Product>) : ProductListState()
    data class Error(val error: String) : ProductListState()
}

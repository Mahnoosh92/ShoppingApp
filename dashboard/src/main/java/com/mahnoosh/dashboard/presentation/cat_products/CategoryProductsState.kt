package com.mahnoosh.dashboard.presentation.cat_products

import androidx.paging.PagingData
import com.mahnoosh.core.data.models.local.Product

sealed class CategoryProductsState {
    object Loading : CategoryProductsState()
    data class Products(val products: PagingData<Product>) : CategoryProductsState()
    data class Error(val error: String) : CategoryProductsState()
}
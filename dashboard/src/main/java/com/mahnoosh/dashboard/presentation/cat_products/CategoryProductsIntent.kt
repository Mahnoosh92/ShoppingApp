package com.mahnoosh.dashboard.presentation.cat_products

sealed class CategoryProductsIntent {
    data class GetCategoryProducts(val id: Int, val limit: Int, val offset: Int) :
        CategoryProductsIntent()
}
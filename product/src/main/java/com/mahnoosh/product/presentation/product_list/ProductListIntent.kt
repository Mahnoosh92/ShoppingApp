package com.mahnoosh.product.presentation.product_list

import com.mahnoosh.core.data.models.local.Product

sealed class ProductListIntent {
    object Products : ProductListIntent()
}

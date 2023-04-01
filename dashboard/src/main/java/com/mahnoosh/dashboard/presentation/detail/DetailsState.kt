package com.mahnoosh.dashboard.presentation.detail

import com.mahnoosh.core.data.models.local.Product

sealed class DetailsState {
    data class GetProduct(val product: Product?) : DetailsState()

    data class Error(val error: String) : DetailsState()

    object Loading : DetailsState()
}
package com.mahnoosh.dashboard.presentation.detail

sealed class DetailsIntent {
    data class GetProduct(val id: Int?) :
        DetailsIntent()
}
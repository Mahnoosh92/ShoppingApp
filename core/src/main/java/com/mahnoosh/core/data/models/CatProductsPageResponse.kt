package com.mahnoosh.core.data.models

import com.mahnoosh.core.data.models.remote.RemoteProduct

data class CatProductsPageResponse(
    val results: List<RemoteProduct>
)

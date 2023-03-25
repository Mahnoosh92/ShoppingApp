package com.mahnoosh.core.data.models.local

import com.mahnoosh.dashboard.data.models.local.Category

data class Product(
    val category: Category,
    val creationAt: String?,
    val description: String?,
    val id: Int?,
    val images: List<String>?,
    val price: Int?,
    val title: String?,
    val updatedAt: String?
)

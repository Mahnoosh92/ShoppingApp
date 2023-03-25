package com.mahnoosh.core.data.models.remote

import com.google.gson.annotations.SerializedName
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.data.models.remote.RemoteCategory

data class RemoteProduct(
    val category: RemoteCategory,
    @SerializedName("creationAt") val creationAt: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("images") val images: List<String>,
    @SerializedName("price") val price: Int,
    @SerializedName("title") val title: String,
    @SerializedName("updatedAt") val updatedAt: String
) {
    fun toProduct() = Product(
        category = category.toCategory(),
        creationAt = creationAt,
        description = description,
        id = id,
        images = images,
        price = price,
        title = title,
        updatedAt = updatedAt
    )
}
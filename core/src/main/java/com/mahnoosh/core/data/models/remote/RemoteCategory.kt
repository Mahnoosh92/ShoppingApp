package com.mahnoosh.dashboard.data.models.remote

import com.google.gson.annotations.SerializedName
import com.mahnoosh.dashboard.data.models.local.Category

data class RemoteCategory(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("creationAt") val creationAt: String?,
    @SerializedName("updatedAt") val updatedAt: String?,
) {
    fun toCategory() = Category(
        id = this.id,
        name = this.name,
        image = this.image,
        creationAt = this.creationAt,
        updatedAt = this.updatedAt
    )
}

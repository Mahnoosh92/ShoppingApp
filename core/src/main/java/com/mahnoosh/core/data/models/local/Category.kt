package com.mahnoosh.dashboard.data.models.local

import com.google.gson.annotations.SerializedName
import com.mahnoosh.dashboard.data.models.local.entity.CategoryEntity

data class Category(
    val id: Int?,
    val name: String?,
    val image: String?,
    val creationAt: String?,
    val updatedAt: String?,
) {
    fun toCategoryEntity(): CategoryEntity = CategoryEntity(
        id = this.id,
        name = this.name,
        image = this.image,
        creationAt = this.creationAt,
        updatedAt = this.updatedAt
    )
}

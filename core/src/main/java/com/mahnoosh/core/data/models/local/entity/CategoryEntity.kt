package com.mahnoosh.dashboard.data.models.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahnoosh.dashboard.data.models.local.Category


@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false) val id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "creationAt") val creationAt: String?,
    @ColumnInfo(name = "updatedAt") val updatedAt: String?,
) {
    fun toCategory() = Category(
        id = this.id,
        name = this.name,
        image = this.image,
        creationAt = this.creationAt,
        updatedAt = this.updatedAt
    )
}
package com.mahnoosh.dashboard.data.datasource.local.category

import com.mahnoosh.dashboard.data.models.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {
    fun getCategories(): Flow<List<CategoryEntity>>
    fun updateCategories(categories: List<CategoryEntity>?): Flow<Unit>
    fun clear(): Flow<Unit>
}
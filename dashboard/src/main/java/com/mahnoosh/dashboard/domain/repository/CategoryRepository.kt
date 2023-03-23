package com.mahnoosh.dashboard.domain.repository

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.dashboard.data.models.local.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<ResultWrapper<Exception, List<Category>>>
}
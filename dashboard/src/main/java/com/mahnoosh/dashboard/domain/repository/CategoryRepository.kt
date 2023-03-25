package com.mahnoosh.dashboard.domain.repository

import androidx.paging.PagingData
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.data.models.local.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(limit: Int): Flow<ResultWrapper<Exception, List<Category>>>
    suspend fun getCategoryProducts(
        id: Int,
        limit: Int,
        offset: Int
    ): Flow<PagingData<Product>>
}
package com.mahnoosh.dashboard.data.datasource.remote.category

import androidx.paging.PagingData
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.CatProductsPageResponse
import com.mahnoosh.core.data.models.remote.RemoteProduct
import com.mahnoosh.dashboard.data.models.local.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRemoteDataSource {
    suspend fun getCategories(limit: Int): Flow<ResultWrapper<java.lang.Exception, List<Category>?>>
    suspend fun getCategoryProducts(
        id: Int,
        limit: Int,
        offset: Int
    ): Flow<PagingData<RemoteProduct>>
}
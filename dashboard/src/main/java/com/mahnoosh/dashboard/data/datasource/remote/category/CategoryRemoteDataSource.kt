package com.mahnoosh.dashboard.data.datasource.remote.category

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.dashboard.data.models.local.Category
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CategoryRemoteDataSource {
    suspend fun getCategories(): Flow<ResultWrapper<java.lang.Exception, List<Category>?>>
}
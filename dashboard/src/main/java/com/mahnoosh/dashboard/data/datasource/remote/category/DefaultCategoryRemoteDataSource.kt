package com.mahnoosh.dashboard.data.datasource.remote.category

import com.mahnoosh.core.api.ApiService
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.utils.extensions.getApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class DefaultCategoryRemoteDataSource(private val apiService: ApiService) :
    CategoryRemoteDataSource {
    override suspend fun getCategories(): Flow<ResultWrapper<Exception, List<Category>?>> {
        return flow {
            if (apiService.getCategories().isSuccessful) {
                emit(ResultWrapper.build {
                    apiService.getCategories().body()?.filterNotNull()?.map {
                        it.toCategory()
                    }
                })
            } else {
                emit(ResultWrapper.build {
                    throw Exception(apiService.getCategories().getApiError()?.message)
                })
            }
        }
    }

}
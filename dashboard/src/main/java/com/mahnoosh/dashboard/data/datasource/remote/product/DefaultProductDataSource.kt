package com.mahnoosh.dashboard.data.datasource.remote.product

import com.mahnoosh.core.api.ApiService
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.utils.extensions.getApiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DefaultProductDataSource(
    private val apiService: ApiService, private val ioDispatcher: CoroutineDispatcher
) : ProductDataSource {

    override suspend fun getProducts(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<Exception, List<Product?>?>> {
        val result = apiService.getProducts(limit = limit, offset = offset)
        return flow {
            if (result.isSuccessful) {
                emit(ResultWrapper.build {
                    result.body()?.map {
                        it?.toProduct()
                    }
                })
            } else {
                emit(ResultWrapper.build {
                    throw java.lang.Exception(
                        result.getApiError()?.message ?: "Something went wrong!"
                    )
                })
            }
        }.flowOn(ioDispatcher).catch {
            emit(ResultWrapper.build {
                throw java.lang.Exception(it.message ?: "Something went wrong!")
            })
        }
    }
}
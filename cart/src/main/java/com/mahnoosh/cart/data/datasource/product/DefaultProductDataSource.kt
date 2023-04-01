package com.mahnoosh.cart.data.datasource.product

import com.mahnoosh.core.api.ApiService
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class DefaultProductDataSource(private val apiService: ApiService) : ProductDataSource {
    override suspend fun getProduct(id: Int): Flow<ResultWrapper<Exception, Product?>> {
        val result = apiService.getProduct(id)
        return flow {
            if (result.isSuccessful) {
                emit(ResultWrapper.build { result.body()?.toProduct() })
            } else {
                emit(ResultWrapper.build { throw Exception(result.message()) })
            }
        }
    }
}
package com.mahnoosh.dashboard.domain.repository

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<Exception, List<Product?>?>>

    suspend fun getProduct(id: Int): Flow<ResultWrapper<Exception, Product?>>
}
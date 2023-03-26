package com.mahnoosh.dashboard.data.repository

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.data.datasource.remote.product.ProductDataSource
import com.mahnoosh.dashboard.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class DefaultProductRepository(private val productDataSource: ProductDataSource) :
    ProductRepository {
    override suspend fun getProducts(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<Exception, List<Product?>?>> {
        TODO("Not yet implemented")
    }
}
package com.mahnoosh.product.data.repository

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.product.data.datasource.remote.product.ProductRemoteDataSource
import com.mahnoosh.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class DefaultProductRepository(private val productRemoteDataSource: ProductRemoteDataSource) :
    ProductRepository {
    override suspend fun getProducts(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<Exception, List<Product?>?>> =
        productRemoteDataSource.getProducts(limit = limit, offset = offset)
}
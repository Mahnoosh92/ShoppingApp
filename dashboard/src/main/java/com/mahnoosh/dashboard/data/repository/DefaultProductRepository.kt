package com.mahnoosh.dashboard.data.repository

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.data.datasource.remote.product.ProductDataSource
import com.mahnoosh.dashboard.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    private val productDataSource: ProductDataSource, private val ioDispatcher: CoroutineDispatcher
) : ProductRepository {
    override suspend fun getProducts(
        limit: Int, offset: Int
    ): Flow<ResultWrapper<Exception, List<Product?>?>> = withContext(ioDispatcher) {
        productDataSource.getProducts(limit = limit, offset = offset)
    }

    override suspend fun getProduct(id: Int): Flow<ResultWrapper<Exception, Product?>> =
        withContext(ioDispatcher) {
            productDataSource.getProduct(id = id)
        }
}
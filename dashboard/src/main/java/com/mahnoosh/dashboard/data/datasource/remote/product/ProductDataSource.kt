package com.mahnoosh.dashboard.data.datasource.remote.product

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    suspend fun getProducts(limit:Int, offset: Int): Flow<ResultWrapper<Exception, List<Product?>?>>
}
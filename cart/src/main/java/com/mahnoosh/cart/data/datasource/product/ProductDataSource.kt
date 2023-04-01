package com.mahnoosh.cart.data.datasource.product

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

interface ProductDataSource {
    suspend fun getProduct(id: Int): Flow<ResultWrapper<Exception, Product?>>
}
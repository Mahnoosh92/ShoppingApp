package com.mahnoosh.cart.domain.repository

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCarts(): Flow<List<ResultWrapper<Exception, Product?>>>
}
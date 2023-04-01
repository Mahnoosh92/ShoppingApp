package com.mahnoosh.cart.data.datasource.cart

import com.mahnoosh.cart.data.model.remote.Cart
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CartDataSource  {
    suspend fun getCarts(): Flow<ResultWrapper<Exception, Cart?>>
}
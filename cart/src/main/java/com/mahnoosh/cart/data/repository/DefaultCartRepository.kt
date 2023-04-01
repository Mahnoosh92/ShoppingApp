package com.mahnoosh.cart.data.repository

import android.util.Log
import com.mahnoosh.cart.data.datasource.cart.CartDataSource
import com.mahnoosh.cart.data.datasource.product.ProductDataSource
import com.mahnoosh.cart.domain.repository.CartRepository
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class DefaultCartRepository(
    private val cartDataSource: CartDataSource,
    private val productDataSource: ProductDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CartRepository {

    override suspend fun getCarts(): Flow<List<ResultWrapper<Exception, Product?>>> {
        return flowOf(
            cartDataSource.getCarts()
                .map { resultWrapper ->
                    if (resultWrapper is ResultWrapper.Error) {
                        throw java.lang.Exception(resultWrapper.error)
                    }
                    (resultWrapper as ResultWrapper.Value).value?.products!!
                }
                .flatMapMerge {
                    Log.i("reza", "getCarts: ${it.size} ")
                    it.asFlow()
                }
                .flatMapConcat {
                    Log.i("reza", "getCarts: ${it.productId} ")
                    productDataSource.getProduct(it.productId ?: -1)
                }
                .flowOn(ioDispatcher)
                .toList()
        )
    }
}
package com.mahnoosh.cart.data.datasource.cart

import com.mahnoosh.cart.data.model.remote.Cart
import com.mahnoosh.cart.data.model.remote.CartProduct
import com.mahnoosh.core.api.ApiService
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.remote.RemoteCart
import com.mahnoosh.core.data.models.remote.RemoteCartProduct
import com.mahnoosh.utils.extensions.getApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultCartDataSource @Inject constructor(private val apiService: ApiService) :
    CartDataSource {
    override suspend fun getCarts(): Flow<ResultWrapper<Exception, Cart?>> {
        val result = apiService.getCarts()
        return flow {
            if (result.isSuccessful) {
                emit(ResultWrapper.build { result.body()?.toCart() })
            } else {
                emit(ResultWrapper.build { throw Exception(result.getApiError()?.message) })
            }
        }
    }
}

fun RemoteCart.toCart() = Cart(
    date = this.date,
    id = this.id,
    products = this.products?.map { it.toCartProduct() },
    userId = this.userId
)

fun RemoteCartProduct.toCartProduct() =
    CartProduct(productId = this.productId, quantity = this.quantity)
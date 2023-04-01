package com.mahnoosh.cart.data.di

import com.mahnoosh.cart.data.datasource.cart.CartDataSource
import com.mahnoosh.cart.data.datasource.product.ProductDataSource
import com.mahnoosh.cart.data.repository.DefaultCartRepository
import com.mahnoosh.cart.domain.repository.CartRepository
import com.mahnoosh.utils.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val CartRepositoryModule = module {
    factory {
        provideCartRepository(
            cartDataSource = get(),
            productDataSource = get(),
            ioDispatcher = get(named(IO_DISPATCHER))
        )
    }
}

private fun provideCartRepository(
    cartDataSource: CartDataSource,
    productDataSource: ProductDataSource,
    ioDispatcher: CoroutineDispatcher
): CartRepository =
    DefaultCartRepository(
        cartDataSource = cartDataSource,
        productDataSource = productDataSource,
        ioDispatcher = ioDispatcher
    )
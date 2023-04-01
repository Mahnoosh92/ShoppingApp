package com.mahnoosh.cart.data.di

import com.mahnoosh.cart.data.datasource.cart.CartDataSource
import com.mahnoosh.cart.data.datasource.cart.DefaultCartDataSource
import com.mahnoosh.cart.data.datasource.product.DefaultProductDataSource
import com.mahnoosh.cart.data.datasource.product.ProductDataSource
import com.mahnoosh.core.api.ApiService
import org.koin.dsl.module

val CartDataSourceModule = module {
    factory {
        provideCartDataSource(apiService = get())
    }
    factory {
        provideProductDataSource(apiService = get())
    }
}

private fun provideCartDataSource(
    apiService: ApiService
): CartDataSource = DefaultCartDataSource(apiService = apiService)

private fun provideProductDataSource(
    apiService: ApiService
): ProductDataSource = DefaultProductDataSource(apiService = apiService)
package com.mahnoosh.product.data.di

import com.mahnoosh.core.api.ApiService
import com.mahnoosh.product.data.datasource.remote.product.DefaultProductRemoteDataSource
import com.mahnoosh.product.data.datasource.remote.product.ProductRemoteDataSource
import com.mahnoosh.utils.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val ProductDataSourceModule = module {
    factory {
        provideProductDataSource(
            apiService = get(), ioDispatcher = get(named(IO_DISPATCHER))
        )
    }
}

private fun provideProductDataSource(
    apiService: ApiService, ioDispatcher: CoroutineDispatcher
): ProductRemoteDataSource =
    DefaultProductRemoteDataSource(apiService = apiService, ioDispatcher = ioDispatcher)
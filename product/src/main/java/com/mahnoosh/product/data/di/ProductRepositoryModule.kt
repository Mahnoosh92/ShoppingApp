package com.mahnoosh.product.data.di

import com.mahnoosh.product.data.datasource.remote.product.ProductRemoteDataSource
import com.mahnoosh.product.data.repository.DefaultProductRepository
import com.mahnoosh.product.domain.repository.ProductRepository
import org.koin.dsl.module

val ProductRepositoryModule = module {
    factory {
        provideProductRepository(productRemoteDataSource = get())
    }
}

private fun provideProductRepository(productRemoteDataSource: ProductRemoteDataSource): ProductRepository =
    DefaultProductRepository(productRemoteDataSource = productRemoteDataSource)

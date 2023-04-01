package com.mahnoosh.dashboard.data.di

import com.mahnoosh.dashboard.data.datasource.local.category.CategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.CategoryRemoteDataSource
import com.mahnoosh.dashboard.data.datasource.remote.product.ProductDataSource
import com.mahnoosh.dashboard.data.repository.DefaultCategoryRepository
import com.mahnoosh.dashboard.data.repository.DefaultProductRepository
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import com.mahnoosh.dashboard.domain.repository.ProductRepository
import com.mahnoosh.utils.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val RepositoryModule = module {
    factory {
        provideCategoryRepository(categoryRemoteDataSource = get(), categoryLocalDataSource = get())
    }
    factory {
        provideProductRepository(productDataSource = get(), ioDispatcher = get(named(IO_DISPATCHER)))
    }
}

private fun provideCategoryRepository(
    categoryLocalDataSource: CategoryLocalDataSource,
    categoryRemoteDataSource: CategoryRemoteDataSource
): CategoryRepository = DefaultCategoryRepository(
    categoryLocalDataSource = categoryLocalDataSource,
    categoryRemoteDataSource = categoryRemoteDataSource
)

private fun provideProductRepository(
    productDataSource: ProductDataSource, ioDispatcher: CoroutineDispatcher
): ProductRepository = DefaultProductRepository(
    productDataSource = productDataSource, ioDispatcher = ioDispatcher
)
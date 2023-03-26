package com.mahnoosh.dashboard.data.di

import com.mahnoosh.dashboard.data.datasource.local.category.CategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.CategoryRemoteDataSource
import com.mahnoosh.dashboard.data.datasource.remote.product.ProductDataSource
import com.mahnoosh.dashboard.data.repository.DefaultCategoryRepository
import com.mahnoosh.dashboard.data.repository.DefaultProductRepository
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import com.mahnoosh.dashboard.domain.repository.ProductRepository
import org.koin.dsl.module

val RepositoryModule = module {
    factory {
        provideCategoryRepository(categoryRemoteDataSource = get(), categoryLocalDataSource = get())
    }
    factory {
        provideProductRepository(productDataSource = get())
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
    productDataSource: ProductDataSource
): ProductRepository = DefaultProductRepository(
    productDataSource = productDataSource
)
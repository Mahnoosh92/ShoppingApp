package com.mahnoosh.dashboard.data.di

import com.mahnoosh.dashboard.data.datasource.local.category.CategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.CategoryRemoteDataSource
import com.mahnoosh.dashboard.data.repository.DefaultCategoryRepository
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import org.koin.dsl.module

val RepositoryModule = module {
    factory {
        provideCategoryRepository(categoryRemoteDataSource = get(), categoryLocalDataSource = get())
    }
}

private fun provideCategoryRepository(
    categoryLocalDataSource: CategoryLocalDataSource,
    categoryRemoteDataSource: CategoryRemoteDataSource
) : CategoryRepository = DefaultCategoryRepository(
    categoryLocalDataSource = categoryLocalDataSource,
    categoryRemoteDataSource = categoryRemoteDataSource
)
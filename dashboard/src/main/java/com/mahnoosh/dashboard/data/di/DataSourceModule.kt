package com.mahnoosh.dashboard.data.di

import com.mahnoosh.core.api.ApiService
import com.mahnoosh.dashboard.data.datasource.local.category.CategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.local.category.DefaultCategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.CategoryRemoteDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.DefaultCategoryRemoteDataSource
import com.mahnoosh.dashboard.data.db.CategoryDao
import com.mahnoosh.utils.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val DataSourceModule = module {
    factory {
        provideCategoryRemoteDataSource(
            apiService = get(), ioDispatcher = get(named(IO_DISPATCHER))
        )
    }
    factory {
        provideCategoryLocalDataSource(categoryDao = get())
    }
}

private fun provideCategoryRemoteDataSource(
    apiService: ApiService, ioDispatcher: CoroutineDispatcher
): CategoryRemoteDataSource =
    DefaultCategoryRemoteDataSource(apiService = apiService, ioDispatcher = ioDispatcher)

private fun provideCategoryLocalDataSource(categoryDao: CategoryDao): CategoryLocalDataSource =
    DefaultCategoryLocalDataSource(categoryDao)


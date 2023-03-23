package com.mahnoosh.dashboard.data.di

import com.mahnoosh.core.api.ApiService
import com.mahnoosh.dashboard.data.datasource.local.category.CategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.local.category.DefaultCategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.CategoryRemoteDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.DefaultCategoryRemoteDataSource
import com.mahnoosh.dashboard.data.db.CategoryDao
import org.koin.dsl.module

val DataSourceModule = module {
    factory {
        provideCategoryRemoteDataSource(apiService = get())
    }
    factory {
        provideCategoryLocalDataSource(categoryDao = get())
    }
}

private fun provideCategoryRemoteDataSource(apiService: ApiService): CategoryRemoteDataSource =
    DefaultCategoryRemoteDataSource(apiService)

private fun provideCategoryLocalDataSource(categoryDao: CategoryDao): CategoryLocalDataSource =
    DefaultCategoryLocalDataSource(categoryDao)
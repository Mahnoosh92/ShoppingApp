package com.mahnoosh.dashboard.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.data.datasource.local.category.CategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.CategoryRemoteDataSource
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DefaultCategoryRepository(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getCategories(limit: Int): Flow<ResultWrapper<Exception, List<Category>>> {
        return categoryRemoteDataSource.getCategories(limit = limit)
            .map { result ->
                var resultTemp: List<Category?>? = null
                when (result) {
                    is ResultWrapper.Value -> {
                        resultTemp = result.value
                    }
                    is ResultWrapper.Error -> {
                        ResultWrapper.build { throw Exception(result.error.message) }
                    }
                }
                resultTemp
            }
            .flatMapConcat { resultTmp ->
                categoryLocalDataSource.clear()
                flowOf(resultTmp)
            }
            .flatMapConcat { resultTmp ->
                val res = resultTmp?.filterNotNull()?.map { it.toCategoryEntity() }
                categoryLocalDataSource.updateCategories(categories = res)
            }
            .flatMapConcat {
                categoryLocalDataSource.getCategories()
            }
            .map {
                ResultWrapper.build {
                    it.map { it.toCategory() }
                }
            }
    }

    override suspend fun getCategoryProducts(
        id: Int,
        limit: Int,
        offset: Int
    ): Flow<PagingData<Product>> {
        return categoryRemoteDataSource.getCategoryProducts(id = id, limit = limit, offset = offset)
            .map { paging ->
                paging.map {
                    it.toProduct()
                }
            }
    }
}
package com.mahnoosh.dashboard.data.repository

import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.dashboard.data.datasource.local.category.CategoryLocalDataSource
import com.mahnoosh.dashboard.data.datasource.remote.category.CategoryRemoteDataSource
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import java.lang.Exception

class DefaultCategoryRepository(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : CategoryRepository {
    override suspend fun getCategories(): Flow<ResultWrapper<Exception, List<Category>>> {
        return categoryRemoteDataSource.getCategories()
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
}
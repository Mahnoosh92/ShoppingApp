package com.mahnoosh.dashboard.data.datasource.remote.category

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mahnoosh.core.api.ApiService
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.CatProductsPageResponse
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.core.data.models.remote.RemoteProduct
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.dashboard.presentation.cat_products.CategoryProductsPaging
import com.mahnoosh.dashboard.presentation.cat_products.PAGE_SIZE
import com.mahnoosh.utils.extensions.getApiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class DefaultCategoryRemoteDataSource(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) :
    CategoryRemoteDataSource {
    override suspend fun getCategories(limit: Int): Flow<ResultWrapper<Exception, List<Category>?>> {
        return flow {
            val res = apiService.getCategories(limit = limit)
            if (res.isSuccessful) {
                emit(ResultWrapper.build {
                    res.body()?.filterNotNull()?.map {
                        it.toCategory()
                    }
                })
            } else {
                emit(ResultWrapper.build {
                    throw Exception(res.getApiError()?.message)
                })
            }
        }
            .flowOn(ioDispatcher)
            .catch {
                emit(ResultWrapper.build { throw Exception(it.message) })
            }
    }

    override suspend fun getCategoryProducts(
        id: Int,
        limit: Int,
        offset: Int
    ): Flow<PagingData<RemoteProduct>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CategoryProductsPaging(id = id, service = apiService)
            }
        ).flow
    }


}
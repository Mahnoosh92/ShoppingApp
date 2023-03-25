package com.mahnoosh.dashboard.presentation.cat_products

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mahnoosh.core.api.ApiService
import com.mahnoosh.core.data.models.remote.RemoteProduct

const val PAGE_SIZE = 1
private const val INITIAL_LOAD_SIZE = 1

class CategoryProductsPaging(
    private val id: Int,
    private val service: ApiService,
) : PagingSource<Int, RemoteProduct>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteProduct> {
        // Start refresh at position 1 if undefined.
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset =
            if (params.key != null) ((position - 1) * PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE
        return try {
            val jsonResponse =
                service.getCategoryProducts(id = id, limit = params.loadSize, offset = offset)
            val response = if (jsonResponse.isSuccessful) jsonResponse.body()
                ?.filterNotNull() else emptyList<RemoteProduct>()
            val nextKey = if (response.isNullOrEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / PAGE_SIZE)
            }
            LoadResult.Page(
                data = response ?: emptyList<RemoteProduct>(),
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RemoteProduct>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return null
    }
}
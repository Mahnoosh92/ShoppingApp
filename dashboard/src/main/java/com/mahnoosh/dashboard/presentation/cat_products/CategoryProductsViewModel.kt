package com.mahnoosh.dashboard.presentation.cat_products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import com.mahnoosh.dashboard.presentation.DashboardIntent
import com.mahnoosh.dashboard.presentation.DashboardState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CategoryProductsViewModel(
    private val categoryRepository: CategoryRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val catProductsIntent = Channel<CategoryProductsIntent>(Channel.UNLIMITED)

    private val _state = MutableLiveData<CategoryProductsState>()
    val state: LiveData<CategoryProductsState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.value = CategoryProductsState.Error(
            error = exception.message ?: "Something went wrong!"
        )
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler) {
            catProductsIntent.consumeAsFlow().collect {
                when (it) {
                    is CategoryProductsIntent.GetCategoryProducts -> getCatProducts(
                        id = it.id,
                        limit = it.limit,
                        offset = it.offset
                    )
                }
            }
        }
    }

    private fun getCatProducts(id: Int, limit: Int, offset: Int) {
        viewModelScope.launch(exceptionHandler) {
            categoryRepository.getCategoryProducts(id = id, limit = limit, offset = offset)
                .collect { result ->
                    _state.value = CategoryProductsState.Products(products = result)
                }
        }
    }
}
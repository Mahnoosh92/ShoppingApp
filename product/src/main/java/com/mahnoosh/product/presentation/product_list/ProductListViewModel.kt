package com.mahnoosh.product.presentation.product_list

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.product.R
import com.mahnoosh.product.domain.repository.ProductRepository
import com.mahnoosh.utils.providers.ResourceProvider
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val LIMIT = 25
const val OFFSET = 1

class ProductListViewModel(
    private val productRepository: ProductRepository, private val resourceProvider: ResourceProvider
) : ViewModel() {

    val productListIntent = Channel<ProductListIntent>(Channel.UNLIMITED)

    private val _state = MutableLiveData<ProductListState>()
    val state: LiveData<ProductListState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _state.postValue(
                ProductListState.Error(
                    exception.message ?: resourceProvider.getString(
                        R.string.general_error
                    )
                )
            )
        }
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler) {
            productListIntent.consumeAsFlow().collect {
                when (it) {
                    is ProductListIntent.Products -> {
                        getProducts()
                    }
                }
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch(exceptionHandler) {
            productRepository.getProducts(limit = LIMIT, offset = OFFSET).catch {
                _state.value = ProductListState.Error(
                    it.message ?: resourceProvider.getString(R.string.general_error)
                )
            }.collect {
                when (it) {
                    is ResultWrapper.Value -> {
                        _state.value = ProductListState.Products(
                            products = it.value?.filterNotNull() ?: emptyList<Product>()
                        )
                    }
                    is ResultWrapper.Error -> {
                        _state.value = ProductListState.Error(
                            it.error.message ?: resourceProvider.getString(R.string.general_error)
                        )
                    }
                }
            }
        }
    }
}
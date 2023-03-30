package com.mahnoosh.product.presentation.product.list

import androidx.lifecycle.*
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.product.R
import com.mahnoosh.product.domain.repository.ProductRepository
import com.mahnoosh.utils.providers.ResourceProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val LIMIT = 25
const val OFFSET = 1

class ProductListViewModel(
    private val productRepository: ProductRepository,
    private val resourceProvider: ResourceProvider,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    val productListIntent = Channel<ProductListIntent>(Channel.UNLIMITED)

    private val _state = MutableLiveData<ProductListState>()
    val state: LiveData<ProductListState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch(mainDispatcher) {
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
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
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
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            productRepository.getProducts(limit = LIMIT, offset = OFFSET)
                .catch {
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
                                it.error.message
                                    ?: resourceProvider.getString(R.string.general_error)
                            )
                        }
                    }
                }
        }
    }
}
package com.mahnoosh.dashboard.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.domain.repository.ProductRepository
import com.mahnoosh.dashboard.presentation.cat_products.CategoryProductsIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableLiveData<DetailsState>(DetailsState.Loading)
    val state: LiveData<DetailsState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.value = DetailsState.Error(error = exception.message ?: "Something went wrong!")
    }

    val detailsIntent = Channel<DetailsIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            detailsIntent.consumeAsFlow().collect {
                when (it) {
                    is DetailsIntent.GetProduct -> getProducts(
                        id = it.id
                    )
                }
            }
        }
    }

    private fun getProducts(id: Int?) {
        if (id != null) {
            viewModelScope.launch(exceptionHandler + mainDispatcher) {
                delay(700L)
                productRepository.getProduct(id = id).catch {
                    _state.value = DetailsState.Error(error = it.message ?: "Something went wrong!")
                }.collect { result ->
                    when (result) {
                        is ResultWrapper.Error -> {
                            _state.value = DetailsState.Error(
                                error = result.error.message ?: "Something went wrong!"
                            )
                        }
                        is ResultWrapper.Value -> {
                            _state.value = DetailsState.GetProduct(product = result.value)
                        }
                    }
                }
            }
        }
    }
}
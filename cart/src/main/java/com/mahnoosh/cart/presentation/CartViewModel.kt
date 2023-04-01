package com.mahnoosh.cart.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.cart.domain.repository.CartRepository
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.data.models.local.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    val cartIntent = Channel<CartIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<CartState>(CartState.Loading)
    val state: StateFlow<CartState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch(mainDispatcher) {
            _state.value = (
                    CartState.Error(error = exception.message ?: "Something went wrong"))
        }
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            cartIntent.consumeAsFlow().collect {
                when (it) {
                    is CartIntent.GetCarts -> {
                        getCarts()
                    }
                }
            }
        }
    }

    private fun getCarts() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            cartRepository.getCarts()
                .collect { result ->
                    val list = mutableListOf<Product>()
                    result.forEach { resultWrapper ->
                        when (resultWrapper) {
                            is ResultWrapper.Value -> {
                                if (resultWrapper.value != null) {
                                    list.add((resultWrapper.value) as Product)
                                }
                            }
                            is ResultWrapper.Error -> {}
                        }
                    }
                }
        }
    }
}
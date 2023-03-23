package com.mahnoosh.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val categoryRepository: CategoryRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val dashboardIntent = Channel<DashboardIntent>(Channel.UNLIMITED)

    private val _state = MutableSharedFlow<DashboardState>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val state: SharedFlow<DashboardState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _state.emit(DashboardState.Error(exception.message ?: "Something went wrong!"))
        }
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler) {
            dashboardIntent.consumeAsFlow().collect {
                when (it) {
                    is DashboardIntent.GetCategories -> getCategories()
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch(exceptionHandler) {
            categoryRepository.getCategories()
                .flowOn(ioDispatcher)
                .catch {
                    _state.emit(DashboardState.Error(error = it.message ?: "Something went wrong!"))
                }
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Value -> {
                            _state.emit(DashboardState.Categories(categories = result.value))
                        }
                        is ResultWrapper.Error -> {
                            _state.emit(
                                DashboardState.Error(
                                    error = result.error.message ?: "Something went wrong!"
                                )
                            )
                        }
                    }
                }
        }
    }
}
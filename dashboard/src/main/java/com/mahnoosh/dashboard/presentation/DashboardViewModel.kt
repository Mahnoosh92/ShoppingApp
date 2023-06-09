package com.mahnoosh.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.core.domain.repository.UserRepository
import com.mahnoosh.dashboard.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val LIMIT = 20

class DashboardViewModel(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    val dashboardIntent = Channel<DashboardIntent>(Channel.UNLIMITED)

    private val _state =
        MutableSharedFlow<DashboardState>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val state: SharedFlow<DashboardState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch(mainDispatcher) {
            _state.emit(DashboardState.Error(exception.message ?: "Something went wrong!"))
        }
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            dashboardIntent.consumeAsFlow().collect {
                when (it) {
                    is DashboardIntent.GetCategories -> getCategories()
                    is DashboardIntent.SignOut -> signOut()
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            categoryRepository.getCategories(limit = LIMIT)
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

    private fun signOut() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            userRepository.signOut().flowOn(ioDispatcher).collect {
                _state.emit(
                    DashboardState.SignOut
                )
            }
        }
    }
}
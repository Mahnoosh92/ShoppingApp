package com.mahnoosh.auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.core.base.ResultWrapper
import com.mahnoosh.auth.domain.usecase.user.UserUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SplashViewModel(val userUseCase: UserUseCase) : ViewModel() {

    val splashIntent = Channel<SplashIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state: StateFlow<SplashState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.value = SplashState.Error(exception.message ?: "Something went wrong!")
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler) {
            splashIntent.consumeAsFlow().collect {
                when (it) {
                    is SplashIntent.GetUser -> getUser()
                }
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch(exceptionHandler) {
            delay(2000L)
            userUseCase.getUser()
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Value -> {
                            _state.value = SplashState.User(result.value)
                        }
                        is ResultWrapper.Error -> {
                            _state.value =
                                SplashState.Error(result.error.message ?: "Something went wrong!")
                        }
                    }
                }
        }
    }
}



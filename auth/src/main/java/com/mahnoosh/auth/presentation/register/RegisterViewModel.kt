package com.mahnoosh.auth.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.auth.domain.repository.AuthRepository
import com.mahnoosh.auth.presentation.login.LoginIntent
import com.mahnoosh.auth.presentation.login.LoginState
import com.mahnoosh.auth.presentation.splash.SplashIntent
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {
    val registerIntent = Channel<RegisterIntent>(Channel.UNLIMITED)

    private val _state = MutableLiveData<RegisterState>()
    val state: LiveData<RegisterState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.value = RegisterState.Error(exception.message ?: "Something went wrong!")
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            registerIntent.consumeAsFlow().collect {
                when (it) {
                    is RegisterIntent.RegisterUser -> register(it.email, it.password)
                }
            }
        }
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            val result =
                authRepository.createUserWithEmailAndPassword(email = email, password = password)
            when (result) {
                is ResultWrapper.Value -> {
                    _state.value = RegisterState.RegisterStatus(status = true)
                }
                is ResultWrapper.Error -> {
                    _state.value =
                        RegisterState.Error(result.error.message ?: "Something went wrong!")
                }
            }
        }
    }
}
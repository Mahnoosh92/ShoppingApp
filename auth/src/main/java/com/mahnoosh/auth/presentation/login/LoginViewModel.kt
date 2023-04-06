package com.mahnoosh.auth.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahnoosh.auth.domain.repository.AuthRepository
import com.mahnoosh.auth.presentation.splash.SplashIntent
import com.mahnoosh.auth.presentation.splash.SplashState
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {
    val loginIntent = Channel<LoginIntent>(Channel.UNLIMITED)

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState>
        get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.value = LoginState.Error(exception.message ?: "Something went wrong!")
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch(exceptionHandler + mainDispatcher) {
            loginIntent.consumeAsFlow().collect {
                when (it) {
                    is LoginIntent.Login -> login(it.email, it.password)
                    is LoginIntent.CreateAccount -> createAccount()
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch(mainDispatcher) {
            val result =
                authRepository.signInWithEmailAndPassword(email = email, password = password)
            when (result) {
                is ResultWrapper.Value -> {
                    _state.postValue(LoginState.LoginStatus(status = true))
                }
                is ResultWrapper.Error -> {
                    _state.value =
                        (LoginState.Error(result.error.message ?: "Something went wrong!"))
                }
            }
        }
    }

    private fun createAccount() {
        _state.postValue(LoginState.NoAccount)
    }
}
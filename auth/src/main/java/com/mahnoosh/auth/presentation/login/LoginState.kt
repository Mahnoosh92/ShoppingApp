package com.mahnoosh.auth.presentation.login

sealed class LoginState {
    object Loading : LoginState()
    object NoAccount : LoginState()
    data class LoginStatus(val status: Boolean) : LoginState()
    data class Error(val error: String) : LoginState()
}

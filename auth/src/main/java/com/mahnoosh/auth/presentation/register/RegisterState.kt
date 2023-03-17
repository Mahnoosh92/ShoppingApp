package com.mahnoosh.auth.presentation.register


sealed class RegisterState {
    object Loading : RegisterState()
    data class RegisterStatus(val status: Boolean) : RegisterState()
    data class Error(val error: String) : RegisterState()
}
package com.mahnoosh.auth.presentation.register


sealed class RegisterIntent {
    data class RegisterUser(val email: String, val password: String) : RegisterIntent()
}
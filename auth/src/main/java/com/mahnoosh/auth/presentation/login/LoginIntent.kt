package com.mahnoosh.auth.presentation.login


sealed class LoginIntent {
    data class Login(val email: String, val password: String) : LoginIntent()
    object CreateAccount : LoginIntent()
}
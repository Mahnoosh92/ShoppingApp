package com.mahnoosh.auth.presentation.splash

import com.google.firebase.auth.FirebaseUser

sealed class SplashState {
    object Loading : SplashState()
    data class User(val user: FirebaseUser?) : SplashState()
    data class Error(val error: String) : SplashState()
}

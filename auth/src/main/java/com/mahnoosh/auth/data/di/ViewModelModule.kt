package com.mahnoosh.auth.data.di

import com.mahnoosh.auth.domain.repository.AuthRepository
import com.mahnoosh.auth.presentation.splash.SplashViewModel
import com.mahnoosh.auth.domain.usecase.user.UserUseCase
import com.mahnoosh.auth.presentation.login.LoginViewModel
import com.mahnoosh.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        provideSplashViewModel(userUseCase = get())
    }
    viewModel {
        provideLoginViewModel(authRepository = get())
    }
    viewModel {
        provideRegisterViewModel(authRepository = get())
    }
}

private fun provideSplashViewModel(userUseCase: UserUseCase) =
    SplashViewModel(userUseCase = userUseCase)

private fun provideLoginViewModel(authRepository: AuthRepository) =
    LoginViewModel(authRepository = authRepository)

private fun provideRegisterViewModel(authRepository:AuthRepository) =
    RegisterViewModel(authRepository = authRepository)
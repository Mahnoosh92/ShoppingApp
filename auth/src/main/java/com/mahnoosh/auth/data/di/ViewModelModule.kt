package com.mahnoosh.auth.data.di

import com.mahnoosh.auth.domain.repository.AuthRepository
import com.mahnoosh.auth.presentation.splash.SplashViewModel
import com.mahnoosh.auth.domain.usecase.user.UserUseCase
import com.mahnoosh.auth.presentation.login.LoginViewModel
import com.mahnoosh.auth.presentation.register.RegisterViewModel
import com.mahnoosh.utils.IO_DISPATCHER
import com.mahnoosh.utils.MAIN_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        provideSplashViewModel(userUseCase = get())
    }
    viewModel {
        provideLoginViewModel(authRepository = get(), mainDispatcher = get(named(MAIN_DISPATCHER)))
    }
    viewModel {
        provideRegisterViewModel(
            authRepository = get(),
            mainDispatcher = get(named(MAIN_DISPATCHER))
        )
    }
}

private fun provideSplashViewModel(userUseCase: UserUseCase) =
    SplashViewModel(userUseCase = userUseCase)

private fun provideLoginViewModel(
    authRepository: AuthRepository,
    mainDispatcher: CoroutineDispatcher
) =
    LoginViewModel(authRepository = authRepository, mainDispatcher = mainDispatcher)

private fun provideRegisterViewModel(
    authRepository: AuthRepository,
    mainDispatcher: CoroutineDispatcher
) =
    RegisterViewModel(authRepository = authRepository, mainDispatcher = mainDispatcher)
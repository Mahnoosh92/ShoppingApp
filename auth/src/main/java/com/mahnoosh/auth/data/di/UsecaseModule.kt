package com.mahnoosh.auth.data.di

import com.mahnoosh.auth.domain.repository.UserRepository
import com.mahnoosh.auth.domain.usecase.user.DefaultUserUseCase
import com.mahnoosh.auth.domain.usecase.user.UserUseCase
import org.koin.dsl.module


val useCaseModule = module {
    factory {
        provideUserUseCase(userRepository = get())
    }
}

private fun provideUserUseCase(userRepository: UserRepository): UserUseCase =
    DefaultUserUseCase(userRepository = userRepository)
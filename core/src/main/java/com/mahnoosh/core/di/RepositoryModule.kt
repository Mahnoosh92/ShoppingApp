package com.mahnoosh.core.di

import com.mahnoosh.core.data.datasource.remote.UserDataSource
import com.mahnoosh.core.data.repository.DefaultUserRepository
import com.mahnoosh.core.domain.repository.UserRepository
import org.koin.dsl.module

val RepositoryModule = module {
    single {
        provideUserRepository(userDataSource = get())
    }
}

private fun provideUserRepository(userDataSource: UserDataSource): UserRepository =
    DefaultUserRepository(userDataSource = userDataSource)
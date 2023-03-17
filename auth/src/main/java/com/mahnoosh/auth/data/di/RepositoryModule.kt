package com.mahnoosh.auth.data.di

import com.mahnoosh.auth.data.datasource.remote.AuthDataSource
import com.mahnoosh.auth.data.datasource.remote.user.UserDataSource
import com.mahnoosh.auth.data.repository.DefaultAuthRepository
import com.mahnoosh.auth.data.repository.DefaultUserRepository
import com.mahnoosh.auth.domain.repository.AuthRepository
import com.mahnoosh.auth.domain.repository.UserRepository
import com.mahnoosh.utils.IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module


val repositoryModule = module {
    factory {
        provideAuthRepository(dataSource = get(), ioDispatcher = get(named(IO_DISPATCHER)))
    }
    factory {
        provideUserRepository(userDataSource = get(), ioDispatcher = get(named(IO_DISPATCHER)))
    }
}

private fun provideAuthRepository(
    dataSource: AuthDataSource,
    ioDispatcher: CoroutineDispatcher
): AuthRepository =
    DefaultAuthRepository(dataSource = dataSource, ioDispatcher = ioDispatcher)

private fun provideUserRepository(userDataSource: UserDataSource, ioDispatcher: CoroutineDispatcher):UserRepository = DefaultUserRepository(userDataSource = userDataSource, ioDispatcher = ioDispatcher)
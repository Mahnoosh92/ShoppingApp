package com.mahnoosh.auth.data.di

import com.google.firebase.auth.FirebaseAuth
import com.mahnoosh.auth.data.datasource.remote.AuthDataSource
import com.mahnoosh.auth.data.datasource.remote.DefaultAuthDataSource
import com.mahnoosh.auth.data.datasource.remote.user.DefaultUserDataSource
import com.mahnoosh.auth.data.datasource.remote.user.UserDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory {
        provideAuthDataSource(auth = get())
    }
    factory {
        provideUserDataSource(auth = get())
    }
}

private fun provideAuthDataSource(auth: FirebaseAuth): AuthDataSource = DefaultAuthDataSource(auth)
private fun provideUserDataSource(auth: FirebaseAuth): UserDataSource = DefaultUserDataSource(auth)
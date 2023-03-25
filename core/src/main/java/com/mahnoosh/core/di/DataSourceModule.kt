package com.mahnoosh.core.di

import com.google.firebase.auth.FirebaseAuth
import com.mahnoosh.core.data.datasource.remote.DefaultUserDataSource
import com.mahnoosh.core.data.datasource.remote.UserDataSource
import org.koin.dsl.module

val DataSourceModule = module {
    single {
        provideUserDataSource(auth = get())
    }
}

private fun provideUserDataSource(auth: FirebaseAuth): UserDataSource =
    DefaultUserDataSource(auth = auth)
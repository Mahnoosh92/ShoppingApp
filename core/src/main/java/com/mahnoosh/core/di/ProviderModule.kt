package com.mahnoosh.core.di

import android.content.Context
import com.mahnoosh.utils.providers.DefaultResourceProvider
import com.mahnoosh.utils.providers.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ProviderModule = module {
    single {
        provideResource(context = androidContext())
    }
}

private fun provideResource(context: Context): ResourceProvider =
    DefaultResourceProvider(context = context)
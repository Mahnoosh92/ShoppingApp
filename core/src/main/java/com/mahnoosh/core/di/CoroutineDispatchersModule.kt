package com.mahnoosh.core.di

import com.mahnoosh.utils.DEFAULT_DISPATCHER
import com.mahnoosh.utils.IO_DISPATCHER
import com.mahnoosh.utils.MAIN_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineDispatchersModule = module {
    single(named(IO_DISPATCHER)) {
        providesIoDispatcher()
    }
    single(named(MAIN_DISPATCHER)) {
        providesMainDispatcher()
    }
    single(named(DEFAULT_DISPATCHER)) {
        providesDefaultDispatcher()
    }
}

private fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

private fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

private fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
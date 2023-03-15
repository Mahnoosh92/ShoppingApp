package com.mahnoosh.shoppingapp

import android.app.Application
import com.mahnoosh.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    coroutineDispatchersModule,
                    dataSourceModule,
                    networkModule,
                    notificationModule,
                    persistentModule,
                    repositoryModule,
                    utilsModule
                )
            )
        }
    }
}
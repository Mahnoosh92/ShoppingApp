package com.mahnoosh.shoppingapp

import android.app.Application
import com.mahnoosh.auth.data.di.viewModelModule
import com.mahnoosh.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    coroutineDispatchersModule,
                    networkModule,
                    notificationModule,
                    persistentModule,
                    utilsModule,
                    firebaseModule,
                    DataSourceModule,
                    RepositoryModule,
                    ProviderModule
                )
            )
        }
    }
}
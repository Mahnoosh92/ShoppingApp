package com.mahnoosh.auth

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.mahnoosh.MyApplication
import com.mahnoosh.auth.data.di.FirebaseTestModule
import com.mahnoosh.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyTestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
            modules(
                listOf(
                    coroutineDispatchersModule,
                    networkModule,
                    notificationModule,
                    persistentModule,
                    utilsModule,
                    FirebaseTestModule,
                    DataSourceModule,
                    RepositoryModule,
                    ProviderModule
                )
            )
        }
    }
}
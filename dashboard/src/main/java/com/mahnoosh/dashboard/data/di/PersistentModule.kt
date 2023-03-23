package com.mahnoosh.dashboard.data.di

import android.content.Context
import androidx.room.Room
import com.mahnoosh.dashboard.data.db.DashboardDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val PersistentModule = module {
    single {
        provideDashboardDatabase(appContext = androidContext())
    }
    single {
        provideCategoryDao(db = get())
    }
}

private fun provideDashboardDatabase(appContext: Context) = Room.databaseBuilder(
    appContext, DashboardDataBase::class.java, "dashboard_database"
)
    .fallbackToDestructiveMigration()
    .build()

private fun provideCategoryDao(db: DashboardDataBase) = db.categoryDao()


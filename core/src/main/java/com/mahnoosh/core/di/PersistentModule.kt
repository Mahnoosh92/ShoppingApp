package com.mahnoosh.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.mahnoosh.core.data.db.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val ON_BOARDING_PREFERENCES = "on_boarding_preferences"
private const val USER_PREFERENCES_NAME = "user_preferences"
private const val DATA_STORE_FILE_NAME = "user_prefs.pb"

val persistentModule = module {
    single {
        provideAppDatabase(appContext = androidContext())
    }
    single {
        providePreferencesDataStore(appContext = androidContext())
    }
    single {
        provideTestDao(db = get())
    }
}

private fun provideAppDatabase(appContext: Context) = Room.databaseBuilder(
    appContext, AppDataBase::class.java, "item_database"
)
    .fallbackToDestructiveMigration()
    .build()

private fun provideTestDao(db: AppDataBase) = db.testDao()
private fun providePreferencesDataStore(appContext: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }),
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        produceFile = { appContext.preferencesDataStoreFile(ON_BOARDING_PREFERENCES) }
    )
}

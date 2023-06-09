package com.mahnoosh.core.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single {
        provideFirebaseAuth()
    }
}

private fun provideFirebaseAuth(): FirebaseAuth {
    return Firebase.auth
}

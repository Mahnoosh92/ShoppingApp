package com.mahnoosh.auth.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val FirebaseTestModule = module {
    single {
        provideFirebaseAuth()
    }
}

private fun provideFirebaseAuth(): FirebaseAuth {
    return Firebase.auth
}

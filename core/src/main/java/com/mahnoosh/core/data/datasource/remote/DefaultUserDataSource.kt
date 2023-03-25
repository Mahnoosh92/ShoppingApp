package com.mahnoosh.core.data.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserDataSource(private val auth: FirebaseAuth) : UserDataSource {
    override fun signOut(): Flow<Unit> = flow {
        auth.signOut()
        emit(Unit)
    }
}
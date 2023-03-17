package com.mahnoosh.auth.data.datasource.remote.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserDataSource(val auth: FirebaseAuth?) : UserDataSource {
    override fun getUser(): Flow<FirebaseUser?> = flow {
        emit(auth?.currentUser)
    }
}
package com.mahnoosh.auth.data.datasource.remote.user

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun getUser(): Flow<FirebaseUser?>
}
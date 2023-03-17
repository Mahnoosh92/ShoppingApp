package com.mahnoosh.auth.data.datasource.remote

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult
    suspend fun createUserWithEmailAndPassword(email: String, password: String): AuthResult
}
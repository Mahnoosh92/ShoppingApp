package com.mahnoosh.auth.domain.repository

import com.google.firebase.auth.AuthResult
import com.mahnoosh.core.base.ResultWrapper

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): ResultWrapper<Exception, AuthResult>
    suspend fun createUserWithEmailAndPassword(email: String, password: String): ResultWrapper<Exception, AuthResult>
}
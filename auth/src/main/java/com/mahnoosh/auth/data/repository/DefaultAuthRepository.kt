package com.mahnoosh.auth.data.repository

import com.google.firebase.auth.AuthResult
import com.mahnoosh.auth.data.datasource.remote.AuthDataSource
import com.mahnoosh.auth.domain.repository.AuthRepository
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultAuthRepository(
    private val dataSource: AuthDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): ResultWrapper<Exception, AuthResult> = withContext(ioDispatcher) {
        ResultWrapper.build {
            dataSource.signInWithEmailAndPassword(email = email, password = password)
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): ResultWrapper<Exception, AuthResult> = withContext(ioDispatcher) {
        ResultWrapper.build {
            dataSource.createUserWithEmailAndPassword(email = email, password = password)
        }
    }
}
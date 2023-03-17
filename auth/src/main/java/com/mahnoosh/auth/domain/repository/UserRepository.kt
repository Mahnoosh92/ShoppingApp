package com.mahnoosh.auth.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<ResultWrapper<Exception,FirebaseUser?>>
}
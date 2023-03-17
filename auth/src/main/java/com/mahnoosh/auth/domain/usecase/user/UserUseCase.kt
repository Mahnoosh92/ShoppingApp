package com.mahnoosh.auth.domain.usecase.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getUser(): Flow<ResultWrapper<Exception, FirebaseUser?>>
}
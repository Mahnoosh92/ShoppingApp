package com.mahnoosh.auth.data.repository

import com.google.firebase.auth.FirebaseUser
import com.mahnoosh.auth.data.datasource.remote.user.UserDataSource
import com.mahnoosh.auth.domain.repository.UserRepository
import com.mahnoosh.core.base.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DefaultUserRepository(
   private val userDataSource: UserDataSource, private val ioDispatcher: CoroutineDispatcher
) : UserRepository {
    override fun getUser(): Flow<ResultWrapper<Exception, FirebaseUser?>> {
        return userDataSource.getUser().map {
            ResultWrapper.build {
                it
            }
        }.catch {
            throw it
        }.flowOn(ioDispatcher)
    }
}
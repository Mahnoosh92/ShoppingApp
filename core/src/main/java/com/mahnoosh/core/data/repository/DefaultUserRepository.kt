package com.mahnoosh.core.data.repository

import com.mahnoosh.core.data.datasource.remote.UserDataSource
import com.mahnoosh.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DefaultUserRepository(private val userDataSource: UserDataSource) : UserRepository {
    override fun signOut(): Flow<Unit> {
        return userDataSource.signOut()
    }
}
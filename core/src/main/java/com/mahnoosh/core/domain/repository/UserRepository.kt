package com.mahnoosh.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun signOut(): Flow<Unit>
}
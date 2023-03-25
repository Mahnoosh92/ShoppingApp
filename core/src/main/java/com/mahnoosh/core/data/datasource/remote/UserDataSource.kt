package com.mahnoosh.core.data.datasource.remote

import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun signOut(): Flow<Unit>
}
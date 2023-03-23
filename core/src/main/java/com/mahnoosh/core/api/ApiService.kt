package com.mahnoosh.core.api

import com.mahnoosh.dashboard.data.models.remote.RemoteCategory
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("categories")
    suspend fun getCategories(): Response<List<RemoteCategory?>?>
}
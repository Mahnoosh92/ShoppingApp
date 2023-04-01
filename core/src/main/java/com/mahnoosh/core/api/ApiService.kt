package com.mahnoosh.core.api

import com.mahnoosh.core.data.models.remote.RemoteCart
import com.mahnoosh.core.data.models.remote.RemoteProduct
import com.mahnoosh.dashboard.data.models.remote.RemoteCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("categories")
    suspend fun getCategories(@Query("limit") limit: Int): Response<List<RemoteCategory?>?>

    @GET("categories/{id}/products")
    suspend fun getCategoryProducts(
        @Path("id") id: Int, @Query("limit") limit: Int, @Query("offset") offset: Int
    ): Response<List<RemoteProduct?>?>

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<List<RemoteProduct?>?>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<RemoteProduct?>

    @GET("carts")
    suspend fun getCarts(): Response<RemoteCart?>
}
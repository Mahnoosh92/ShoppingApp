package com.mahnoosh.core.data.models.remote

import com.google.gson.annotations.SerializedName

data class RemoteCart(
    @SerializedName("date") val date: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("products") val products: List<RemoteCartProduct>?,
    @SerializedName("userId") val userId: Int?
)

data class RemoteCartProduct(
    @SerializedName("productId") val productId: Int?, @SerializedName("quantity") val quantity: Int?
) {
   
}
package com.mahnoosh.utils.extensions

import com.google.gson.Gson
import com.mahnoosh.core.data.models.local.MyError
import retrofit2.Response

fun Response<*>.getApiError(): MyError? {
    return try {
        val errorJsonString = this.errorBody()?.string()
        Gson().fromJson(errorJsonString, MyError::class.java)
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}
package com.mahnoosh.utils.providers

interface ResourceProvider {
    fun getString(resId: Int): String
}
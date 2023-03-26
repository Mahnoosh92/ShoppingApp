package com.mahnoosh.utils.providers

import android.content.Context

class DefaultResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(resId: Int): String = context.getString(resId)
}
package com.gas.test.ui.kotlin

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object CacheUtils {

    private val context = TestApplication.currentApplication
    private val SP = context.getSharedPreferences("hencoder", Context.MODE_PRIVATE)

    //类型推断简化函数声明
    fun save(key: String?, value: String) = SP.edit().putString(key, value).apply()
    fun get(key: String?): String? = SP.getString(key, null)
}
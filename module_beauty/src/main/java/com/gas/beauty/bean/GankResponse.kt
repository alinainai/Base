package com.gas.beauty.bean

import androidx.annotation.Keep

@Keep
data class GankResponse<T> (
    val error:Boolean = false,
    val results: T? = null
)
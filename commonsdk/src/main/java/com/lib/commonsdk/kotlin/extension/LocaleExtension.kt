package com.lib.commonsdk.kotlin.extension

import java.util.Locale

/**
 * Locale的扩展函数
 */
val Locale.normalLocale: String
    get() = language + "_" + country
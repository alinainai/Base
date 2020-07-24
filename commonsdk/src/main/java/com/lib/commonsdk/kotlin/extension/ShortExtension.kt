package com.lib.commonsdk.kotlin.extension

/**
 * [Short]的扩展。
 */

/**
 * 获取一个Short数据的高位
 */
val Short.higherByte: Byte
    get() = (toInt() ushr 8 and 0xff).toByte()

/**
 * 获取一个Short数据的低位
 */
val Short.lowerByte: Byte
    get() = (toInt() and 0xff).toByte()

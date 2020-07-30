package com.lib.commonsdk.kotlin.extension.number

/**
 * 判断一个字节对应的值是否是数字
 */
fun Byte.isDigit() = toChar().isDigit()


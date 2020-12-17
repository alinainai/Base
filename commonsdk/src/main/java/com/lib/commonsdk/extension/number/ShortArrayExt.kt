package com.lib.commonsdk.extension.number

/**
 * [ShortArray]的扩展。
 */

/**
 * 将一个Short类型的数组转为Byte类型的数组
 */
fun ShortArray.toByteArray(): ByteArray {
    val result = ByteArray(size * 2)
    for (i in 0..(size - 1)) {
        result[i * 2] = get(i).lowerByte
        result[i * 2 + 1] = get(i).higherByte
    }
    return result
}

/**
 * 将一个Short类型的数组转为Double类型的数组
 */
fun ShortArray.toDoubleArray(): DoubleArray = map(Short::toDouble).toDoubleArray()

/**
 * 将一个Short类型数组转Float数组
 * */
fun ShortArray.toFloatArray(): FloatArray = map(Short::toFloat).toFloatArray()
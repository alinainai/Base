package com.lib.commonsdk.kotlin.extension

/**
 * [ByteArray]的扩展
 */

/**
 * 将4个Byte打包成一个Int
 */
fun ByteArray.packToInt(): Int {
    assert(size == 4)

    val part1 = (get(0).toInt() and 0xff) shl 24
    val part2 = (get(1).toInt() and 0xff) shl 16
    val part3 = (get(2).toInt() and 0xff) shl 8
    val part4 = (get(3).toInt() and 0xff)
    return part1 or part2 or part3 or part4
}

/**
 * 将2个Byte打包成一个Short
 */
fun ByteArray.packToShort(): Short {
    assert(size == 2)

    val part1 = (get(0).toInt() and 0xff) shl 8
    val part2 = (get(1).toInt() and 0xff)
    return (part1 or part2).toShort()
}

fun ByteArray.packToShort(cursor: Int): Short {
    if (size < 2 || cursor < 0 || cursor + 2 > size) {
        throw ArrayIndexOutOfBoundsException()
    }

    val part1 = (get(cursor).toInt() and 0xff) shl 8
    val part2 = (get(cursor + 1).toInt() and 0xff)
    return (part1 or part2).toShort()
}

private val HEX_CHARS = "0123456789abcdef".toCharArray()

/**
 * 将ByteArray转换成Hex字符串
 * from: https://gist.github.com/fabiomsr/845664a9c7e92bafb6fb0ca70d4e44fd
 */
fun ByteArray.toHex(): String {
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}

/**
 * 将ByteArray转换为ShortArray
 */
fun ByteArray.toShortArray(): ShortArray =
    this.asList()
        .chunked(2)
        .map { (l, h) -> (l.toInt() + h.toInt().shl(8)).toShort() }
        .toShortArray()
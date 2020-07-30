package com.lib.commonsdk.kotlin.extension.number

import kotlin.experimental.and

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

fun ByteArray.isUtf8(): Int {
    if (size > 3) {
        if (this[0] == 0xEF.toByte() && this[1] == 0xBB.toByte() && this[2] == 0xBF.toByte()) {
            return 100
        }
    }
    var utf8 = 0
    var ascii = 0
    var child = 0
    var i = 0
    while (i < size) {
        // UTF-8 byte shouldn't be FF and FE
        if (this[i] and 0xFF.toByte() == 0xFF.toByte() || this[i] and 0xFE.toByte() == 0xFE.toByte()){
            return 0
        }
        if (child == 0) {
            // ASCII format is 0x0*******
            if (this[i] and 0x7F.toByte() == this[i] && this[i] != 0.toByte()) {
                ascii++
            } else if (this[i] and 0xC0.toByte() == 0xC0.toByte()){
                // 0x11****** maybe is UTF-8
                for (bit in 0..7) {
                    child = if ((0x80 shr bit).toByte() and this[i] == (0x80 shr bit).toByte()) {
                        bit
                    } else {
                        break
                    }
                }
                utf8++
            }
            i++
        } else {
            child = if (size - i > child) child else size - i
            var currentNotUtf8 = false
            for (children in 0 until child) {
                // format must is 0x10******
                if (this[i + children] and 0x80.toByte() != 0x80.toByte()) {
                    if (this[i + children] and 0x7F.toByte() == this[i + children] && this[i] != 0.toByte()) {
                        // ASCII format is 0x0*******
                        ascii++
                    }
                    currentNotUtf8 = true
                }
            }
            if (currentNotUtf8) {
                utf8--
                i++
            } else {
                utf8 += child
                i += child
            }
            child = 0
        }
    }
    // UTF-8 contains ASCII
    return if (ascii == size) {
        100
    } else (100 * ((utf8 + ascii).toFloat() / size.toFloat())).toInt()
}
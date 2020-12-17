package com.lib.commonsdk.extension.number

/**
 * [Int]的扩展。
 */

/**
 * 获取一个Int的第n个字节，n可以取值0-3
 * 字节序号从高位向低位排序，n为0时表示取最高位，为3时表示取最低位
 */
fun Int.getByte(index: Int): Byte {
    assert(index in 0..3)
    return (shr((3 - index) * 8) and 0xff).toByte()
}

package com.lib.commonsdk.kotlin.extension

import java.io.IOException
import java.io.StringReader

/**
 * 从StringReader中读一个字符出来
 */
fun StringReader.readChar() = read().toChar()

/**
 * 查看StringReader将要读到的下一个字符
 */
fun StringReader.peekChar() = peek().toChar()

/**
 * 阅读一个字符，同时检查是否满足期望值
 */
fun StringReader.readCharExpected(vararg expected: Char): Char {
    val peeked = peekChar()
    if (peeked !in expected) {
        throw IOException("Unexpected data. (expected '$expected' but was '$peeked'.)")
    }

    return readChar()
}

/**
 * 从一个字符串中读取整数回来
 */
fun StringReader.readInt(): Int {
    val peeked = peekChar()
    if (!peeked.isDigit()) {
        throw IOException("Illegal Format: Next character expected to be a digit but was '$peeked'.")
    }

    var result = 0
    while (peekChar().isDigit()) {
        result = result * 10 + (readChar() - '0')
    }

    return result
}

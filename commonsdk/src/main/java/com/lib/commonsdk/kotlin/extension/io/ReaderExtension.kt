package com.lib.commonsdk.kotlin.extension.io

import java.io.IOException
import java.io.Reader

/**
 * 查看Reader下一次将要读到的数据。不同于[Reader.read]，这个操作不会影响到内容。
 */
fun Reader.peek(): Int {
    if (!markSupported()) {
        throw IOException("Cannot peek from an InputStream which does not support mark")
    }

    mark(1)
    val result = read()
    reset()
    return result
}

/**
 * 读一个Byte，但是期待读到的结果是符合需求的。如果不符合需求，就抛异常。
 */
fun Reader.readExpected(vararg expected: Int): Int {
    val peeked = peek()
    if (peeked !in expected) {
        throw IOException("Unexpected data. (expected '$expected' but was '$peeked'.)")
    }

    return read()
}

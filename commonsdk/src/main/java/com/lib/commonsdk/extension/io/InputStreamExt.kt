package com.lib.commonsdk.extension.io

import com.lib.commonsdk.extension.file.createIfAbsent
import com.lib.commonsdk.extension.number.toHex
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.security.DigestInputStream
import java.security.MessageDigest


/**
 * 从给定的InputStream里边读取一个Byte。
 * 如果当前没有数据，则block住，直至有数据了为止。
 */
fun InputStream.readByteBlocked(): Byte {
    return this.read().toByte()
}

/**
 * 同[readByteBlocked]，只是起了一个更简短的名字。
 */
fun InputStream.readByte() = readByteBlocked()

/**
 * 读一个Byte，但是期待读到的结果是符合需求的。如果不符合需求，就抛异常。
 */
fun InputStream.readExpectedByte(vararg expected: Byte): Byte {
    val peeked = peekByte()
    if (peeked !in expected) {
        throw IOException("Unexpected data. (expected '$expected' but was '$peeked'.)")
    }

    return readByte()
}

/**
 * 查看下一个Byte的内容，但是不读取它（不破坏数据流本身）
 */
fun InputStream.peekByte(): Byte {
    if (!markSupported()) {
        throw IOException("Cannot peek from an InputStream which does not support mark")
    }

    mark(1)
    val result = readByte()
    reset()
    return result
}

/**
 * 同[readByteBlocked]，但是这个是可以一下子读取多个Byte。
 */
fun InputStream.readByteArrayBlocked(length: Int): ByteArray {
    val result = ByteArray(length)
    (0 until length).forEach {
        result[it] = readByteBlocked()
    }

    return result
}

/**
 * 在InputStream中定位到满足给定条件的Byte并读出来。
 * 在符合条件的Byte之前的其他Byte都会被直接忽略掉（丢弃）。
 */
fun InputStream.seekToByteWith(condition: (Byte) -> Boolean) {
    var byte = readByteBlocked()
    while (!condition(byte)) {
        byte = readByteBlocked()
    }
}

fun InputStream.toFile(file: File) {
    use { input ->
        file.createIfAbsent().outputStream().use {
            input.copyTo(it)
        }
    }
}

/**
 * 将数据写入文件，同时获取文件md5值。
 */
fun InputStream.toFileCalculatingMd5(file: File): String {
    val digest = MessageDigest.getInstance("MD5").clone() as MessageDigest
    val input = DigestInputStream(this, digest)
    use {
        file.createIfAbsent().outputStream().use {
            input.copyTo(it)
        }
    }
    val md5 = digest.digest().toHex()
    return md5
}



/**
 * 模仿java中读取inputStream的方式
 * 发现现在也有这种需求场景，每次读取固定字节长度的数据，然后用来 "处理/加工/写" 等操作
 * 所以这个实现了这样一个函数
 *
 * @param length 每次读取的字节长度
 * @param process 每次读取的字节需要执行的操作
 */
@Throws(IOException::class)
fun InputStream.readByteArrayOverall(length: Int, process: (date: ByteArray) -> Unit = {}) {
    var byteArray = ByteArray(length)
    use { input ->
        while (input.readByteArrayBlocked(byteArray.size).also { byteArray = it }.isNotEmpty()) {
            process(byteArray)
        }
    }
}

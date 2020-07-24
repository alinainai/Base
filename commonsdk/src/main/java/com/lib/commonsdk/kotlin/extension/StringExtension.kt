package com.lib.commonsdk.kotlin.extension

import android.util.Base64
import java.security.MessageDigest
import kotlin.concurrent.withLock

/**
 * [String]类的扩展
 */
// ------------------------------

fun String.decodeBase64() = String(Base64.decode(this.toByteArray(), Base64.DEFAULT))

fun String.encodeBase64() = String(Base64.encode(this.toByteArray(), Base64.DEFAULT))

fun String.md5(): String {
    lock.withLock {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }
}

fun String.safeToInt(default: Int = 0) = try {
    toInt()
} catch (e: Exception) {
    e.printStackTrace()
    default
}

/**
 * 从一个网络文件链接中截取后缀
 *
 * 注意: 此处只做url为空处理，不做网络资源链接的检验，需要上层自己做判断
 *       主要是考虑到 写死的文件格式list不可靠，会不断有新的文件格式出现
 */
fun String.getSuffixFromUrl(): String {
    return with(this) {
        if (this.isNotBlank()) {
            substring(lastIndexOf(".") + 1)
        } else {
            throw IllegalStateException("url is blank")
        }
    }
}
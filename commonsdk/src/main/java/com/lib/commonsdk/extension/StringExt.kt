package com.lib.commonsdk.extension

import android.util.Base64
import com.lib.commonsdk.extension.file.lock
import java.security.MessageDigest
import kotlin.concurrent.withLock


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

fun String.isSpace(): Boolean {
    var i = 0
    val len = this.length
    while (i < len) {
        if (!Character.isWhitespace(this[i])) {
            return false
        }
        ++i
    }
    return true
}


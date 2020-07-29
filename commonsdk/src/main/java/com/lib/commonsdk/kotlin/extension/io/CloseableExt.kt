package com.lib.commonsdk.kotlin.extension.io

import com.lib.commonsdk.kotlin.extension.app.errorLog
import java.io.Closeable

/**
 * [Closeable]的扩展
 */
// ------------------------------

fun Closeable.closeQuietly() = apply {
    try {
        close()
    } catch (ignored: Throwable) {
        errorLog(ignored)
    }
}
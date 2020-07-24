package com.lib.commonsdk.kotlin.extension

import java.io.Closeable

/**
 * [Closeable]的扩展
 */
// ------------------------------

fun Closeable.closeQuietly() = apply {
    try {
        close()
    } catch (ignored: Throwable) {
    }
}
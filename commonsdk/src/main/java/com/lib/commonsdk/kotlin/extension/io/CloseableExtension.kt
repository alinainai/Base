package com.lib.commonsdk.kotlin.extension.io

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
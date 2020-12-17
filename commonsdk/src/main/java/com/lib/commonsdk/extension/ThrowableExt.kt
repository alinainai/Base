package com.lib.commonsdk.extension

import com.lib.commonsdk.kotlin.utils.isInstanceOf


/**
 * 判断某一个Throwable是否是由某种原因引起的。
 */
fun Throwable.isCausedBy(type: Class<*>): Boolean {
    var throwable: Throwable? = this
    while (throwable != null) {
        if (isInstanceOf(throwable, type)) {
            return true
        }
        throwable = throwable.cause
    }

    return false
}

/**
 * 某一个Throwable的最终用户可读的信息。
 * 这个回头需要再扩展。
 */
val Throwable.readableMessage: String
    get() = message ?: toString()

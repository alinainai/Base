package com.lib.commonsdk.kotlin.extension

/**
 * [Array]的扩展
 */

/**
 * Returns the sum of all values produced by [selector] function applied to each element in the sequence.
 *
 * The operation is _terminal_.
 */
inline fun <T> Sequence<T>.sumByLong(selector: (T) -> Long): Long {
    var sum: Long = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
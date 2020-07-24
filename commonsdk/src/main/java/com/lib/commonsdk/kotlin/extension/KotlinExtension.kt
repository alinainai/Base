package com.lib.commonsdk.kotlin.extension

/**
 * 对现有的Java类库里边的类做的扩展
 */
// ------------------------------

/**
 * 不执行任何操作的闭包。
 */
val nop: Any.() -> Unit = {}

/**
 * 给一个Int色值加上一个透明度。
 * (重载掉原来的透明度)
 */
fun Int.withAlpha(alpha: Float): Int {
    require(alpha >= 0f && alpha <= 1.0f)
    return ((this shl 8) shr 8) + ((0xff * alpha).toInt() shl 24)
}

/**
 * 获取 IntRange 里边可能出现的整数的个数, 范围为闭区间.
 * 比如说 3..5 的 Range 代表可以取 3, 4, 5 三个值,
 * 所以这里的 intCount 值为 3
 */
val IntRange.intCount: Int
    get() = last - first + 1

/**
 * 给定一个整数的Range, 随机返回属于这个Range的整数值.
 */
fun IntRange.random() = start + (Math.random() * intCount).toInt()

/**
 * 随机返回一个Array里边的一项.
 * 如果Array为空, 会抛异常
 */
fun <T> Array<T>.randomItem(): T {
    if (isEmpty()) {
        throw ArrayIndexOutOfBoundsException("array is empty")
    }

    return get((0..size - 1).random())
}

/**
 * 随机返回一个Array里边的一项.
 * 如果Array为空, 会返回null
 */
fun <T> Array<T>.randomItemOrNull() = try {
    randomItem()
} catch (e: ArrayIndexOutOfBoundsException) {
    null
}

fun <T> List<T>.randomItem(): T {
    if (isEmpty()) {
        throw ArrayIndexOutOfBoundsException("list is empty")
    }

    return get((0 until size).random())
}

/**
 * 随机返回一个List里边的一项.
 * 如果List为空, 会返回null
 */
fun <T> List<T>.randomItemOrNull() = try {
    randomItem()
} catch (e: ArrayIndexOutOfBoundsException) {
    null
}

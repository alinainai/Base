package com.lib.commonsdk.kotlin.extension


import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import com.lib.commonsdk.BuildConfig
import java.io.Closeable
import java.text.SimpleDateFormat
import java.util.*

/**
 * 发现好多地方需要 tryCatch 以保证代码安全性
 * 但是我们又不关心它的运行结果，所以单独写了一个方法
 * 将需要运行的代码块用 tryCatch 包裹起来
 */
fun <T : Any> T.runInTryCatch(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        if (BuildConfig.DEBUG)
            e.printStackTrace()
    }
}

/**
 * 等待一定的时间之后执行某个操作
 */
fun doDelayed(delay: Long, f: () -> Unit) = Handler(Looper.getMainLooper()).apply {
    postDelayed({
        f.invoke()
    }, delay)
}

/**
 * 停止当前正在执行的异步任务
 * 传入的参数是[doDelayed]的返回值。
 */
fun cancelDelayedTask(handler: Handler?) {
    handler?.removeCallbacksAndMessages(null)
}

/**
 * 给一个Drawable设置大小, 用这个方法设置之后Drawable是正方形的。
 */
var <T : Drawable> T.boundSize: Int
    get() = bounds.width()
    set(v) {
        setBounds(0, 0, v, v)
    }

/**
 * Date的扩展函数
 */
fun Date.formatDate() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)

fun Date.formatDateToNumber() = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(this)

val Date.isToday: Boolean
    get() = formatDate().equals(Date().formatDate())

fun Date.formatDatetime() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(this)


/**
 * [Closeable]的扩展
 */
fun Closeable.closeQuietely() = apply {
    try {
        close()
    } catch (ignored: Throwable) {
    }
}

/**
 * 判断一个字节对应的值是否是数字
 */
fun Byte.isDigit() = toChar().isDigit()


/**
 * [ByteArray]的扩展
 */

/**
 * 将4个Byte打包成一个Int
 */
fun ByteArray.packToInt(): Int {
    assert(size == 4)

    val part1 = (get(0).toInt() and 0xff) shl 24
    val part2 = (get(1).toInt() and 0xff) shl 16
    val part3 = (get(2).toInt() and 0xff) shl 8
    val part4 = (get(3).toInt() and 0xff)
    return part1 or part2 or part3 or part4
}

/**
 * 将2个Byte打包成一个Short
 */
fun ByteArray.packToShort(): Short {
    assert(size == 2)

    val part1 = (get(0).toInt() and 0xff) shl 8
    val part2 = (get(1).toInt() and 0xff)
    return (part1 or part2).toShort()
}

fun ByteArray.packToShort(cursor: Int): Short {
    if (size < 2 || cursor < 0 || cursor + 2 > size) {
        throw ArrayIndexOutOfBoundsException()
    }

    val part1 = (get(cursor).toInt() and 0xff) shl 8
    val part2 = (get(cursor + 1).toInt() and 0xff)
    return (part1 or part2).toShort()
}

private val HEX_CHARS = "0123456789abcdef".toCharArray()

/**
 * 将ByteArray转换成Hex字符串
 * from: https://gist.github.com/fabiomsr/845664a9c7e92bafb6fb0ca70d4e44fd
 */
fun ByteArray.toHex(): String {
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}

/**
 * 将ByteArray转换为ShortArray
 */
fun ByteArray.toShortArray(): ShortArray =
        this.asList()
                .chunked(2)
                .map { (l, h) -> (l.toInt() + h.toInt().shl(8)).toShort() }
                .toShortArray()


/**
 * 获取一个Int的第n个字节，n可以取值0-3
 * 字节序号从高位向低位排序，n为0时表示取最高位，为3时表示取最低位
 */
fun Int.getByte(index: Int): Byte {
    assert(index in 0..3)
    return (shr((3 - index) * 8) and 0xff).toByte()
}


/**
 * [Short]的扩展。
 */

/**
 * 获取一个Short数据的高位
 */
val Short.higherByte: Byte
    get() = (toInt() ushr 8 and 0xff).toByte()

/**
 * 获取一个Short数据的低位
 */
val Short.lowerByte: Byte
    get() = (toInt() and 0xff).toByte()
/**
 * [ShortArray]的扩展。
 */

/**
 * 将一个Short类型的数组转为Byte类型的数组
 */
fun ShortArray.toByteArray(): ByteArray {
    val result = ByteArray(size * 2)
    for (i in 0 until size) {
        result[i * 2] = get(i).lowerByte
        result[i * 2 + 1] = get(i).higherByte
    }
    return result
}

/**
 * 将一个Short类型的数组转为Double类型的数组
 */
fun ShortArray.toDoubleArray(): DoubleArray = map(Short::toDouble).toDoubleArray()

/**
 * 将一个Short类型数组转Float数组
 * */
fun ShortArray.toFloatArray(): FloatArray = map(Short::toFloat).toFloatArray()

/**
 * Locale的扩展函数
 */
val Locale.normalLocale: String
    get() = language + "_" + country
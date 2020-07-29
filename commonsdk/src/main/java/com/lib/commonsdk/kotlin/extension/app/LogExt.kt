package com.lib.commonsdk.kotlin.extension.app

import com.lib.commonsdk.BuildConfig
import timber.log.Timber

/**
 * 计算Log输出的文件名和行数，方便调试的时候定位
 */
val lineInfo: String?
    get() = Thread.currentThread().stackTrace.getOrNull(5)?.let {
        return "${it.fileName}:${it.lineNumber}"
    }

/**
 * 返回调用位置的调用堆栈，用于调试。
 * 推荐使用方法： debug(callStack)
 */
val callStack: String
    get() = Thread.currentThread().stackTrace.drop(3).joinToString(separator = "\n", prefix = "CallStack:\n-----\n", postfix = "\n-----") {
        "    ${it.className}#${it.methodName} (${it.fileName}:${it.lineNumber})"
    }

/**
 * 给所有的Class上加上TAG, 用于Log输出.
 */
val <T : Any> T.TAG: String
    get() = "${Thread.currentThread().name} ${Thread.currentThread().id} ROBOT($lineInfo) :${javaClass.simpleName}@${hashCode()}"

/**
 * 类名称和HashCode码。用来在Log输出时定位是哪一个类的那一个对象。
 */
val <T : Any> T.classNameAndHashCode: String
    get() = "${javaClass.simpleName}@${hashCode()}"

/**
 * 输出verbose信息
 */
fun <T : Any> T.verbose(s: Any?) {
    if (BuildConfig.DEBUG) {
        Timber.tag(TAG).v(s?.toString() ?: "[null]")
    }
}

/**
 * 输出verbose信息
 */
fun verbose(tag: String, s: Any?) {
    if (BuildConfig.DEBUG) {
        Timber.tag(tag).v(s?.toString() ?: "[null]")
    }
}

/**
 * 输出info信息
 */
fun <T : Any> T.info(s: Any?) {
    if (BuildConfig.DEBUG) {
        Timber.tag(TAG).i(s?.toString() ?: "[null]")
    }
}

fun info(tag: String, s: Any?) {
    if (BuildConfig.DEBUG) {
        Timber.tag(tag).i(s?.toString() ?: "[null]")
    }
}

/**
 * 输出debug信息
 */
fun <T : Any> T.debug(s: Any?) {
    if (BuildConfig.DEBUG) {
        Timber.tag(TAG).d(s?.toString() ?: "[null]")
    }
}

fun debug(tag: String, s: Any?) {
    if (BuildConfig.DEBUG) {
        Timber.tag(tag).d(s?.toString() ?: "[null]")
    }
}

/**
 * 输出error信息
 */
fun <T : Any> T.errorLog(exception: Throwable, message: String? = null) {
    if (!BuildConfig.DEBUG) {
        Timber.tag(TAG).e(exception, message ?: "[null]")
    }
}

fun errorLog(tag: String, exception: Throwable, message: String? = null) {
    if (!BuildConfig.DEBUG) {
        Timber.tag(tag).e(exception, message ?: "[null]")
    }
}
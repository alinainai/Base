package com.lib.commonsdk.kotlin.extension.app

import android.content.Context
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 使用 [ARouter] 根据 `path` 跳转到对应的页面, 这个方法因为没有使用 [Activity]跳转
 * 所以 [ARouter] 会自动给 [android.content.Intent] 加上 Intent.FLAG_ACTIVITY_NEW_TASK
 * 如果不想自动加上这个 Flag 请使用 [ARouter.getInstance] 并传入 [Activity]
 *
 * @param path `path`
 */
fun navigation(path: String?) {
    ARouter.getInstance().build(path).navigation()
}

/**
 * 使用 [ARouter] 根据 `path` 跳转到对应的页面, 如果参数 `context` 传入的不是 [Activity]
 * [ARouter] 就会自动给 [android.content.Intent] 加上 Intent.FLAG_ACTIVITY_NEW_TASK
 * 如果不想自动加上这个 Flag 请使用 [Activity] 作为 `context` 传入
 *
 * @param context
 * @param path
 */
fun navigation(context: Context?, path: String?) {
    ARouter.getInstance().build(path).navigation(context)
}

fun navigation(context: Context?, path: String?, callback: NavCallback?) {
    ARouter.getInstance().build(path).navigation(context, callback)
}

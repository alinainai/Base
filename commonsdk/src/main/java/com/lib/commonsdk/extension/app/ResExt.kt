package com.lib.commonsdk.extension.app

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun getResources(): Resources {
    return app.resources
}

fun getString(@StringRes sId: Int, vararg args: Any?): String {
    return app.resources.getString(sId, *args)
}

fun getDrawable(@DrawableRes sId: Int): Drawable? {
    return ContextCompat.getDrawable(app.applicationContext, sId)
}

@JvmOverloads
@Dimension(unit = Dimension.PX)
fun Number.dp2Px(metrics: DisplayMetrics = Resources.getSystem().displayMetrics): Float {
    return toFloat() * metrics.density
}

@JvmOverloads
@Dimension(unit = Dimension.DP)
fun Number.px2Dp(metrics: DisplayMetrics = Resources.getSystem().displayMetrics): Float {
    return toFloat() / metrics.density
}

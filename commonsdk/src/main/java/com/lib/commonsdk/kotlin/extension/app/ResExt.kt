package com.lib.commonsdk.kotlin.extension.app

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

fun getResources(): Resources {
    return app.resources
}


fun getString(@StringRes sId: Int, vararg args: Any?): String {
    return app.resources.getString(sId, *args)
}

fun getDrawable(@DrawableRes sId: Int): Drawable {
    return app.resources.getDrawable(sId)
}

@JvmOverloads
@Dimension(unit = Dimension.PX)
fun Number.dpToPx(metrics: DisplayMetrics = Resources.getSystem().displayMetrics): Float {
    return toFloat() * metrics.density
}

@JvmOverloads
@Dimension(unit = Dimension.DP)
fun Number.pxToDp(metrics: DisplayMetrics = Resources.getSystem().displayMetrics): Float {
    return toFloat() / metrics.density
}

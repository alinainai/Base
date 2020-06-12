package com.lib.commonsdk.kotlin.extension

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.annotation.Dimension

@JvmOverloads
@Dimension(unit = Dimension.PX)
fun Number.dpToPx(metrics: DisplayMetrics = Resources.getSystem().displayMetrics): Float {
    return toFloat() * metrics.density
}

@JvmOverloads
@Dimension(unit = Dimension.DP)
fun Number.pxToDp(
        metrics: DisplayMetrics = Resources.getSystem().displayMetrics): Float {
    return toFloat() / metrics.density
}
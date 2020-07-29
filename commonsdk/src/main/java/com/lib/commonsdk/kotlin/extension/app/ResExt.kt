package com.lib.commonsdk.kotlin.extension.app

import android.content.res.Resources
import android.graphics.drawable.Drawable
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

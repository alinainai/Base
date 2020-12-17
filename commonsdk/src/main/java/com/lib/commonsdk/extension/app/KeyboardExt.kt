package com.lib.commonsdk.extension.app

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.lib.commonsdk.kotlin.utils.AppUtils.app

/**
 * Hide the soft input.
 *
 * @param activity The activity.
 */
fun hideSoftInput(activity: Activity) {
    var view = activity.currentFocus
    if (view == null) {
        val decorView = activity.window.decorView
        val focusView = decorView.findViewWithTag<View>("keyboardTagView")
        if (focusView == null) {
            view = EditText(activity)
            view.setTag("keyboardTagView")
            (decorView as ViewGroup).addView(view, 1, 1)
        } else {
            view = focusView
        }
        view.requestFocus()
    }
    hideSoftInput(view)
}

/**
 * Hide the soft input.
 *
 * @param view The view.
 */
fun hideSoftInput(view: View) {
    val imm = app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

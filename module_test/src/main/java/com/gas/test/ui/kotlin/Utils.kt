package com.gas.test.ui.kotlin

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast

private val diplayMetrics = Resources.getSystem().displayMetrics

fun dp2px(dp:Float):Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, diplayMetrics)
}
object Utils {

    fun toast(string: String,context: Context){
        toast(string,Toast.LENGTH_SHORT,context)
    }
    fun toast(string: String,duration: Int,context: Context){
        Toast.makeText(context.applicationContext,string,duration)
    }

}
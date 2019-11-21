@file:JvmName("ToastUtils")
package com.gas.test.ui.kotlin

import android.content.res.Resources
import android.util.TypedValue
import android.widget.Toast

private val diplayMetrics = Resources.getSystem().displayMetrics

fun Float.dp2px(): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, diplayMetrics)
}

object Utils {

    //函数参数默认值简化方法参数
//    fun toast(string: String,context: Context){
//        toast(string,Toast.LENGTH_SHORT,context)
//    }
    //防止Java调用不了单参方法
    @JvmOverloads
    @JvmStatic
    fun toast(string: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(TestApplication.currentApplication, string, duration)
    }

}
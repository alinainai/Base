package com.lib.commonsdk.extension.app

import java.util.*

private const val  CHINESE_LANG = "zh"
private const val  CHINA = "CN"


val Locale.normalLocale: String
    get() = language + "_" + country

//是否是简体中文
fun isMainLandLanguage(): Boolean {
    var isCH = false
    val locale = Locale.getDefault()
    if (locale.language == CHINESE_LANG && locale.country == CHINA) {
        isCH = true
    }
    return isCH
}
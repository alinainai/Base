package com.gas.zhihu.utils

fun main() {
    var str = "北京市"
    var str1 = "12北京市"
    var str2 = "*北京市"
    var str3 = "z北京市"
    var str4 = ""
    println(str.toFirstPySpell())
    println(str1.toFirstPySpell())
    println(str2.toFirstPySpell())
    println(str3.toFirstPySpell())
    println(str4.toFirstPySpell())

    println(str.toPinYin())
    println(str1.toPinYin())
    println(str2.toPinYin())
    println(str3.toPinYin())
    println(str4.toPinYin())

    println(str.toAllPySpell())
    println(str1.toAllPySpell())
    println(str2.toAllPySpell())
    println(str3.toAllPySpell())
    println(str4.toAllPySpell())

}
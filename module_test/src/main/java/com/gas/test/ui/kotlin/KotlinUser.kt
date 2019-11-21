package com.gas.test.ui.kotlin

//Any和java中的Object类似，但是没有Object的那样有很多初始化的方法，比如 wait、notify等
//默认的类修饰符是 public
data class KotlinUser constructor(var username: String?, var password: String?) {
    constructor() : this(null, null)

    //operator自定义操作符
//    operator fun plus(index: Int): KotlinUser {
//        return KotlinUser(index.toString(), "ljx")
//    }


}

//fun main() {
//
//    var user = KotlinUser() + 1
//    println(user)
//}
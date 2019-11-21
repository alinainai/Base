package com.gas.test.ui.kotlin

inline fun measureTime(action: () -> Unit) {

    println(">>>>>>")

    val startTime = System.currentTimeMillis()

    action()

    val endTime = System.currentTimeMillis()


    println("<<<<<<  ${endTime - startTime}ms")

}

fun main() {

    measureTime { println("hello world") }

}
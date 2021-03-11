package com.gas.test.utils

import com.google.gson.Gson
import kotlinx.coroutines.*
import org.json.JSONObject
import java.text.DecimalFormat
import kotlin.math.roundToInt


internal object CoroutineTest {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking { // this: CoroutineScope
        println("${System.currentTimeMillis()} Before runBlocking launch")
        launch {
            delay(200L)
            println("${System.currentTimeMillis()} Task from runBlocking")
        }
        println("${System.currentTimeMillis()} Before coroutineScope")
        coroutineScope { // 创建一个协程作用域
            launch {
                delay(500L)
                println("${System.currentTimeMillis()} Task from nested launch")
            }

            delay(100L)
            println("${System.currentTimeMillis()} Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }
        println("${System.currentTimeMillis()} Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
    }

}
package com.gas.test.utils

import com.google.gson.Gson
import kotlinx.coroutines.*
import java.text.DecimalFormat
import kotlin.math.roundToInt


internal object KotlinTest {

    @JvmStatic
    fun main(args: Array<String>) {
//
//        GlobalScope.launch { // 在后台启动一个新的协程并继续
//            delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
//            println("World!") // 在延迟后打印输出
//        }
//        println("Hello,") // 协程已在等待时主线程还在继续
//        Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活

        val arr = intArrayOf(15, 16,1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 )
        reOrderArray(arr)
        for (a in arr) {
            print("$a ")
        }

    }

    private fun logMapData(){
        val str = "{\n" +
                "    \"errorCode\": 0,\n" +
                "    \"errorMsg\": \"成功\",\n" +
                "    \"data\": {\n" +
                "        \"12\": {\n" +
                "            \"id\": \"12\",\n" +
                "            \"apid\": \"\",\n" +
                "            \"plan_id\": \"\",\n" +
                "            \"name\": \"摄像机7天包月\",\n" +
                "            \"intro\": \"摄像机7天包月\",\n" +
                "            \"unit_price\": \"0.01\",\n" +
                "            \"stock_num\": \"0\",\n" +
                "            \"status\": \"0\",\n" +
                "            \"img_url\": \"https://p.ssl.qhimg.com/d/inn/43b75006/img/day_7.png\",\n" +
                "            \"catg_id\": \"1\",\n" +
                "            \"group_id\": \"1\",\n" +
                "            \"sale_time\": \"2015-09-16 18:37:18\",\n" +
                "            \"sale_type\": \"0\",\n" +
                "            \"type\": \"1\",\n" +
                "            \"extend\": {\n" +
                "                \"func_type\": 0,\n" +
                "                \"period\": 604800,\n" +
                "                \"lifetime\": 2592000,\n" +
                "                \"lifetime_type\": \"2\"\n" +
                "            },\n" +
                "            \"operator\": \"zhaoyan1\",\n" +
                "            \"create_time\": \"2015-09-16 18:37:18\",\n" +
                "            \"modify_time\": \"2020-01-19 14:25:23\",\n" +
                "            \"deleted\": \"0\"\n" +
                "        },\n" +
                "        \"13\": {\n" +
                "            \"id\": \"13\",\n" +
                "            \"apid\": \"\",\n" +
                "            \"plan_id\": \"\",\n" +
                "            \"name\": \"摄像机7天包年\",\n" +
                "            \"intro\": \"摄像机7天包年\",\n" +
                "            \"unit_price\": \"0.04\",\n" +
                "            \"stock_num\": \"0\",\n" +
                "            \"status\": \"0\",\n" +
                "            \"img_url\": \"https://p.ssl.qhimg.com/d/inn/43b75006/img/day_7.png\",\n" +
                "            \"catg_id\": \"1\",\n" +
                "            \"group_id\": \"1\",\n" +
                "            \"sale_time\": \"2015-09-16 18:37:18\",\n" +
                "            \"sale_type\": \"0\",\n" +
                "            \"type\": \"1\",\n" +
                "            \"extend\": {\n" +
                "                \"func_type\": 0,\n" +
                "                \"period\": 604800,\n" +
                "                \"lifetime\": 31536000,\n" +
                "                \"lifetime_type\": \"4\"\n" +
                "            },\n" +
                "            \"operator\": \"ximeizhen\",\n" +
                "            \"create_time\": \"2015-09-16 18:37:18\",\n" +
                "            \"modify_time\": \"2020-04-03 18:33:35\",\n" +
                "            \"deleted\": \"0\"\n" +
                "        }\n" +
                "    }\n" +
                "}"
        print(Gson().fromJson(str, DataID1::class.java))
    }

    class DataID1 {
        val errorCode: Int = 0
        val errorMsg: String = ""
        val data: Map<String, Product> = mutableMapOf()

        override fun toString(): String {
            return data.toString()
        }
    }

    data class Product(val id: String, val name: String)

    fun parseJSONP(jsonp: String): Any {
        val startIndex = jsonp.indexOf("(")
        val endIndex = jsonp.lastIndexOf(")")
        val json = jsonp.substring(startIndex + 1, endIndex)
        println(json)
        return Gson().fromJson(json, DataID::class.java)
    }

    class DataID {
        val errorCode: Int = 0
        val errorMsg: String = ""
        val data: Dbean = Dbean()

        override fun toString(): String {
            return data.toString()
        }

        class Dbean {
            val android: List<An> = mutableListOf()
            override fun toString(): String {
                return android.toString()
            }
        }

        data class An(val pid: String, val pname: String)
    }



    /**
     * double类型如果小数点后为零显示整数否则保留 返回String
     * @param num
     * @return
     */
    private fun double2IntOrDiBit(num: Double): String {
        val number1 = String.format("%.2f", num) //只保留小数点后2位
        val number2 = number1.toDouble() //類型轉換
        if (number2.roundToInt() - number2 == 0.0) {
            return number2.toInt().toString()
        }
        return number2.toString()
    }

    /**
     * double类型如果小数点后为零显示整数否则保留 返回String
     * @param num
     * @return
     */
    fun doubleTrans2(num: Double): String {
        return DecimalFormat("#0.00").format(num).toString()
    }


    private fun computeRunTime(action: (() -> Unit)?) {
        val startTime = System.currentTimeMillis()
        action?.invoke()
        println("the code run time is ${System.currentTimeMillis() - startTime}")
    }

    private val computeIteratorRunTime = {
        (0..10000000)
                .map { it + 1 }
                .filter { it % 2 == 0 }
                .count { it < 10 }
                .run {
                    println("by using sequences way, result is : $this")
                }
    }

    //转化成Sequences序列，使用序列操作
    private val computeSequenceRunTime = {
        (0..10000000)
                .asSequence()
                .map { it + 1 }
                .filter { it % 2 == 0 }
                .count { it < 10 }
                .run {
                    println("by using sequences way, result is : $this")
                }
    }


    private fun list2Map() {
        val colors = listOf(Color("red", 1),
                Color("green", 2),
                Color("blue", 3))
        val map = colors.map { it.name to it }.toMap()
        print(map)
    }

    data class Color(val name: String, val value: Int)

    /**
     * 1.要想保证原有次序，则只能顺次移动或相邻交换。
     * 2.i从左向右遍历，找到第一个偶数。
     * 3.j从i+1开始向后找，直到找到第一个奇数。
     * 4.将[i,...,j-1]的元素整体后移一位，最后将找到的奇数放入i位置，然后i++。
     * 5.終止條件：j向後遍歷查找失敗。
     */
    fun reOrderArray(a: IntArray?) {
        if (a == null || a.isEmpty()) return
        var i = 0
        var j: Int
        while (i < a.size) {
            while (i < a.size && !isEven(a[i])) i++
            j = i + 1
            while (j < a.size && isEven(a[j])) j++
            if (j < a.size) {
                val tmp = a[j]
                for (j2 in j - 1 downTo i) {
                    a[j2 + 1] = a[j2]
                }
                a[i++] = tmp
            } else { // 查找失敗
                break
            }
        }
    }

    fun isEven(n: Int): Boolean {
        return n and(1) == 0
    }



}
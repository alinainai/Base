package com.gas.test.utils

internal object KotlinTest {
    @JvmStatic
    fun main(args: Array<String>) {
        //list to map
        list2Map()
        //sequence 和 iterator
        computeRunTime(computeIteratorRunTime)
        computeRunTime(computeSequenceRunTime)



    }


    private fun computeRunTime(action: (() -> Unit)?) {
        val startTime = System.currentTimeMillis()
        action?.invoke()
        println("the code run time is ${System.currentTimeMillis() - startTime}")
    }
    private val computeIteratorRunTime={
        (0..10000000)
                .map { it + 1 }
                .filter { it % 2 == 0 }
                .count { it < 10 }
                .run {
                    println("by using sequences way, result is : $this")
                }
    }
    //转化成Sequences序列，使用序列操作
   private val computeSequenceRunTime ={
        (0..10000000)
                .asSequence()
                .map { it + 1 }
                .filter { it % 2 == 0 }
                .count { it < 10 }
                .run {
                    println("by using sequences way, result is : $this")
                }
    }



    private fun list2Map(){
        val colors = listOf(Color("red",1),
                Color("green",2),
                Color("blue",3))
        val map =  colors.map { it.name to it }.toMap()
        print(map)
    }

    data class Color(val name:String, val value:Int)

}
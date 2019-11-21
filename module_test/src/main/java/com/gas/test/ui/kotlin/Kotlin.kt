package com.gas.test.ui.kotlin

import android.util.Log

//在kotlin当中可以直接把函数声明到kotlin文件中 而不像java只能声明在类中

//1.声明一个函数 fun关键字

//无参数 无返回值
// Unit 和java中的 void 对应
fun main():Unit {
    //"".sout 打印log
    println("hello world!")

    //声明一个变量 ,关键字var，类型后置
//    var age:Int = 10
    var age=10; //Int 可以省略，可以推断出来
    // val 类似于 java中的final
    val name:String = "kotlin"
//    name="AA"; 爆红
    //对象
    var user:KotlinUser=KotlinUser("李佳星","ljx")
    var copy=user.copy()

    println(user)
    println(copy)
    //注意这里的 == 和java不同 和j ava 的 equits 类似
    println(user==copy)
    //=== 是比较地址值
    println(user===copy)

    //非空
    var username=user.username?:"韩桂敏"
    var length=user.username?.length?:0

    //集合
    var users: ArrayList<KotlinUser> = ArrayList()
    users.add(KotlinUser("李佳星","11223333"))
    users.add(KotlinUser("李佳星","11223344"))
    users.add(KotlinUser("李佳星","11223355"))
    users.add(KotlinUser("讲英语","11223366"))
    users.add(KotlinUser("李佳星","11223377"))
    users.add(KotlinUser("李佳星","11223388"))
    users.add(KotlinUser("李佳星","11223399"))
    users.forEach {

        if(it.password!=null&&it.password!!.contains("366")){
            println(it)
        }

    }
    users= users.filter { it.username=="李佳星" } as ArrayList<KotlinUser>
//    println(users)

    //不包括10
//    repeat(10){
//        println(it)
//    }
    val ints = intArrayOf(1,32,44,32,54,34,55)
    //until 不包括最后一位
    for (i in 0 until ints.size){
        println(ints[i])
    }


}

//内联函数
inline fun log(){
    //减少一次调用栈
    //增加编译器编译时间
    Log.e("TAG","123")
    Log.e("TAG","123")
}

//带参数 带返回值 用 :分割
fun doubleX(x: Int): Int {
    return x * 2
}
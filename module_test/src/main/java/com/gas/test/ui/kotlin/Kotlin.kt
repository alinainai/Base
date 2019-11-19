package com.gas.test.ui.kotlin

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
    var java:Java=Java()

}
//带参数 带返回值 用 :分割
fun doubleX(x: Int): Int {
    return x * 2
}
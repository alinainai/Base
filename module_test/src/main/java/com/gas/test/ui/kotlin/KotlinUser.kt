package com.gas.test.ui.kotlin

//Any和java中的Object类似，但是没有Object的那样有很多初始化的方法，比如 wait、notify等
//默认的类修饰符是 public
class KotlinUser : Any{

    //JvmField 可以在java类中直接调用成员变量，不再需要调用get和set方法
    @JvmField
    var username:String?=null
    @JvmField
    var password:String?=null
    //默认的 get set，kotlin 会自动生成
//        set(value)  {
//            field=value
//        }
//        get() {
//            return field
//        }

    constructor(){}

    constructor(username:String,password:String){
        this.username=username
        this.password=password
    }


}
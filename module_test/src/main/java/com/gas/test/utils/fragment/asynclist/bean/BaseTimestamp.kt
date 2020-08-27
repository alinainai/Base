package com.gas.test.utils.fragment.asynclist.bean

abstract class BaseTimestamp (open val timeStamp:Long ){
    //唯一标识符
    abstract fun uniqueId(): Long
    //可变内容
    abstract fun variableParam(): String
}
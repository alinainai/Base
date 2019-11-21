package com.gas.test.ui.kotlin

import android.app.Application
import android.content.Context

class TestApplication:Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        currentApplication=this
    }

    //生成一个内部类
    companion object{
        @JvmStatic
        @get:JvmName("currentApplication")
        lateinit var currentApplication: Context
            private set
    }



}
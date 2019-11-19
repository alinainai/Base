package com.gas.test.ui.kotlin

import android.app.Application
import android.content.Context

class TestApplication:Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    //生成一个内部类
    companion object{

        private lateinit var currentApplication: Application
        @JvmStatic
        fun currentApplication():Context{
            return currentApplication
        }

    }

}
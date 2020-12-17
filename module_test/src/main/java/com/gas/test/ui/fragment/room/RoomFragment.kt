package com.gas.test.ui.fragment.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.lib.commonsdk.extension.app.debug
import kotlinx.android.synthetic.main.fragment_coroutines_layout.*
import kotlinx.coroutines.*


class RoomFragment : BaseFragment<IPresenter>() {
    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_room, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        button.setOnClickListener {
            MainScope().launch {
                val startTime = System.currentTimeMillis()
                debug("tag1：" + Thread.currentThread().name)
                val text1 = getText1()
                val text2 = getText2()
                debug(text1 + text2)
                debug("耗时：" + (System.currentTimeMillis() - startTime))
            }
        }
        button2.setOnClickListener {  }
        button3.setOnClickListener {  }
        button4.setOnClickListener {  }
        button5.setOnClickListener {  }

    }

    private suspend fun getText1():String {
        return withContext(Dispatchers.IO){
            delay(1000)
            "hello"
        }

    }
    private suspend fun getText2():String {
        return withContext(Dispatchers.IO){
            delay(1000)
            "world"
        }
    }

    override fun setData(data: Any?) {
    }

}
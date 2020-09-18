package com.gas.test.utils.lazyload

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


abstract class LazyLoadFragment :LogFragment(){
    private var isFirstLoad = true // 是否第一次加载


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getContentViewId(), null)
    }

    abstract fun getContentViewId(): Int

    override fun onResume() {
        super.onResume()
        if (isFirstLoad&&!isHidden) {
            // 将数据加载逻辑放到onResume()方法中
            lazyInit()
            isFirstLoad = false
        }
    }

    open fun lazyInit(){
        Log.d(TAG, "initData: ")
    }


}
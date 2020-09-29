package com.gas.test.utils.lazyload

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


/**
 * Author:  andy.xwt
 * Date:    2020-01-14 18:29
 * Description: 负责打印日志的Fragment基类
 */

open class LogFragment : Fragment() {

    protected var TAG = javaClass.simpleName

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("LogFragment: $TAG", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LogFragment: $TAG", "onCreate: ")
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d("LogFragment: $TAG", "onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LogFragment: $TAG", "onViewCreated: ")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("LogFragment: $TAG", "onActivityCreated: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("LogFragment: $TAG", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LogFragment: $TAG", "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LogFragment: $TAG", "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LogFragment: $TAG", "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LogFragment: $TAG", "onDestroyView: ")
    }



    override fun onDetach() {
        super.onDetach()
        Log.d("LogFragment: $TAG", "onDetach: ")
    }



    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d("LogFragment: $TAG", "onHiddenChanged:hidden-->$hidden")
    }

}
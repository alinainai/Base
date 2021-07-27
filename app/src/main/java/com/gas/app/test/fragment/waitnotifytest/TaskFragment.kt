package com.gas.app.test.fragment.waitnotifytest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.BasePresenter
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.app.R
import timber.log.Timber
import java.util.*


class TaskFragment : BaseFragment<BasePresenter<IModel, IView>>() {
    override fun setupFragmentComponent(appComponent: AppComponent) {

    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        view?.findViewById<View>(R.id.btnModule1)?.setOnClickListener {
            task()
        }
    }

    override fun setData(data: Any?) {



    }

    companion object {
        @JvmStatic
        fun newInstance(): TaskFragment {
            return TaskFragment()
        }
    }


   private fun task() {
        val q = TaskQueue()
        val ts = ArrayList<Thread>()
        for (i in 0..4) {
            val t: Thread = object : Thread() {
                override fun run() {
                    // 执行task:
                    while (true) {
                        try {
                            val s = q.task
                            Timber.e("execute task: $s")
                        } catch (e: InterruptedException) {
                            return
                        }
                    }
                }
            }
            t.start()
            ts.add(t)
        }
        val add = Thread {
            for (i in 0..9) {
                // 放入task:
                val s = "t-" + Math.random()
                Timber.e("add task: $s")
                q.addTask(s)
                try {
                    Thread.sleep(100)
                } catch (ignored: InterruptedException) {
                }
            }
        }
        add.start()
        add.join()
        Thread.sleep(100)
        for (t in ts) {
            t.interrupt()
        }
    }

}
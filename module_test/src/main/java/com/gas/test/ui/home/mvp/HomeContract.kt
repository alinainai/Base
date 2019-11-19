package com.gas.test.ui.home.mvp

import android.app.Activity

import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView


interface HomeContract {


    interface View : IView {
        val activity: Activity
        fun success()
    }


    interface Model : IModel

}

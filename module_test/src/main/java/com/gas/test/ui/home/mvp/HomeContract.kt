package com.gas.test.ui.home.mvp

import android.content.Context
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView


interface HomeContract {


    interface View : IView {
        fun getWrapContext(): Context
        fun success()
    }


    interface Model : IModel

}

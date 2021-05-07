package com.gas.beauty.ui.home.mvp

import android.app.Activity
import com.base.lib.mvp.IView

interface HomeContract {
    interface View : IView {
        val activity: Activity
    }
}
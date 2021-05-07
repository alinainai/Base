package com.gas.zhihu.ui.fragment.mine.mvp

import android.content.Context
import com.base.lib.mvp.IView

interface MineContract {
    interface View : IView {
        val wrapContext: Context?
    }

    interface Model
}
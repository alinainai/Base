package com.gas.beauty.ui.main.mvp

import android.app.Activity
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.beauty.bean.GankResponse
import com.gas.beauty.bean.BeautyBean
import io.reactivex.Observable

interface MainContract {
    interface View : IView {
        val activity: Activity
        fun success()
        fun onError()
    }

    interface Model : IModel {
        fun getGirlList(num: Int, page: Int): Observable<GankResponse<List<BeautyBean>>>
    }
}
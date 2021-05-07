package com.gas.beauty.ui.classify.mvp

import android.content.Context
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.beauty.bean.GankItemBean
import io.reactivex.Observable

interface ClassifyContract {
    interface View : IView {
        fun loadEnd()
        val wrapContext: Context?
    }

    interface Model : IModel {
        fun getGankItemData(suburl: String): Observable<List<GankItemBean>>
    }
}
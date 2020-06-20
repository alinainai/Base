package com.gas.zhihu.ui.show.mvp

import android.app.Activity
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.zhihu.bean.MapBean

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */
interface ShowContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun setDataInfo(data: MapBean?)
        fun setQrCode(data: String?)
        fun successView()
        fun emptyView()
        fun errorView()
        fun showDeleteSuccessTip()
        fun getActivity():Activity
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun getMapInfo(key: String?): MapBean?
        val defaultMapInfo: MapBean?
        fun deleteMapInfo(key:String?)
    }
}
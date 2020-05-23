package com.gas.zhihu.fragment.paper.mvp

import android.app.Activity
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.PaperShowBean


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */
interface PagerContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun getWrapActivity():Activity
        fun setPaperData(list: List<PaperShowBean>)
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun setType(type:Int)
        fun getValidMapList(): List<MapBean>
        fun getPagersByFilter(voltage: String, mapKey: String): List<PaperShowBean>
    }


}

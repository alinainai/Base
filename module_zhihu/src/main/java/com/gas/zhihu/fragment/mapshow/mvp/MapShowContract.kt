package com.gas.zhihu.fragment.mapshow.mvp

import android.app.Activity
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.fragment.mapshow.bean.ISortBean


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/31/2020 15:45
 * ================================================
 */
interface MapShowContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun getWrapActivity(): Activity
        fun setMapData(t: List<ISortBean>)
        fun setSliderBarTxt(chars:List<String>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun setType(type: Int)
        fun getValidMapList(): List<MapBean>
        fun getSortBeanWithFilter(filter: String?): List<ISortBean>
        fun resetOriginData()
        fun getOrderChars():List<String>
    }

}

package com.gas.zhihu.fragment.papersearch.mvp

import android.app.Activity
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.PaperShowBean


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 06/20/2020 17:46
 * ================================================
 */
interface PaperSearchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun setPaperData(list: List<PaperShowBean>)
        fun getWrapActivity(): Activity
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun setType(type: Int)
        fun getPagersByFilter(paperName: String): List<PaperShowBean>
    }


}

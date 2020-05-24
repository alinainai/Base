package com.gas.zhihu.fragment.fileselect.mvp

import android.app.Activity
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.zhihu.bean.FileItemBean


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/24/2020 11:32
 * ================================================
 */
interface FileSelectContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun getWrapActivity(): Activity
        fun setPaperData(list: List<FileItemBean>)
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
       fun getDocumentData(type: String?): List<FileItemBean>
    }


}

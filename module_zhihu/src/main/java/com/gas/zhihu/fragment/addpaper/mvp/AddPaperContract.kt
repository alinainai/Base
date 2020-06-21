package com.gas.zhihu.fragment.addpaper.mvp

import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.MapSelectShowBean
import com.gas.zhihu.bean.PaperBean
import com.gas.zhihu.bean.VoltageLevelBean


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:45
 * ================================================
 */
interface AddPaperContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView{
        fun showMapSelectDialog(maps:List<MapSelectShowBean>)
        fun showVolSelectDialog(maps:List<VoltageLevelBean>)
        fun showCommitSuccess()
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel{
        fun getMapList():List<MapBean>
        fun getVoltageList():List<VoltageLevelBean>
        fun insertPaperBean(bean: PaperBean):Boolean
        fun updatePaperInfo(bean: PaperBean):Boolean
    }


}
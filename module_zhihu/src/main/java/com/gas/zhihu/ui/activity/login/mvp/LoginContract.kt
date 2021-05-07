package com.gas.zhihu.ui.activity.login.mvp

import com.base.lib.mvp.IView

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/24/2020 20:50
 * ================================================
 */
interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView
}
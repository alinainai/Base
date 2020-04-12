package com.gas.zhihu.ui.map.mvp;

import android.app.Activity;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 21:09
 * ================================================
 */
public interface MapContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
       RxPermissions getRxPermissions();
        Activity getActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        int getMapDataCount();

    }
}

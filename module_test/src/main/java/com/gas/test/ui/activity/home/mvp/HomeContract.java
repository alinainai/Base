package com.gas.test.ui.activity.home.mvp;

import android.content.Context;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;
import com.gas.test.bean.TestInfoBean;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */
public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Context getWrapContext();
        void loadData();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        List<TestInfoBean> getInfoItems();
    }
}

package com.gas.test.ui.fragment.adapter.mvp;

import android.content.Context;

import com.base.lib.mvp.IModel;
import com.base.lib.mvp.IView;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/29/2019 16:48
 * ================================================
 */
public interface AdapterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Context getWrapContent();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        List<String> provideStringItems(int page,int itemCount);
        List<String> provideInsertItems(int itemCount);
        String provideNewItem();

    }
}

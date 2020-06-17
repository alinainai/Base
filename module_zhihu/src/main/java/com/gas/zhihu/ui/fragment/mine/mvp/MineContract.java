package com.gas.zhihu.ui.fragment.mine.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;

public interface MineContract {


    interface View extends IView {
        Context getWrapContext();
    }

    interface Model {

    }


}

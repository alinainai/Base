package com.gas.app.ui.activity.utils.mvp;

import android.content.Context;

import com.base.lib.mvp.IView;

public interface WebViewContract {


    interface View extends IView {
        Context getWrapContext();
    }

}

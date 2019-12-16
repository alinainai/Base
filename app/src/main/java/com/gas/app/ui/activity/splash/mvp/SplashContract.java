package com.gas.app.ui.activity.splash.mvp;

import com.base.lib.mvp.IView;

public interface SplashContract {


    interface View extends IView {
        void goMainActivity();

        void showTimeDown(long time);

        void showVersionCode(String code);
    }

}

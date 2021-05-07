package com.gas.zhihu.ui.activity.login.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;


@ActivityScope
public class LoginPresenter extends BasePresenter<IModel, LoginContract.View> {

    @Inject
    public LoginPresenter(LoginContract.View rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

package com.gas.app.ui.activity.utils.mvp;

import android.app.Application;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;

@ActivityScope
public class WebViewPresenter extends BasePresenter<IModel, WebViewContract.View> {


    @Inject
    Application mApplication;


    @Inject
    public WebViewPresenter(WebViewContract.View rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy() {
        mApplication = null;
        super.onDestroy();
    }

}

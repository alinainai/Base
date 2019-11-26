package com.gas.test.ui.test;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;

@FragmentScope
public class ArticlePresenter extends BasePresenter<IModel,ArticleContract.View> {


    @Inject
    Application mApplication;


    @Inject
    public ArticlePresenter( ArticleContract.View rootView) {
        super( rootView);
    }


    @Override
    public void onDestroy() {
        mApplication = null;
        super.onDestroy();
    }

}

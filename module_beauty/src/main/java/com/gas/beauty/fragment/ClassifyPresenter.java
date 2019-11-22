package com.gas.beauty.fragment;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;

@FragmentScope
public class ClassifyPresenter extends BasePresenter<ClassifyModle, ClassifyContract.View> {


    @Inject
    Application mApplication;


    @Inject
    public ClassifyPresenter(ClassifyModle model, ClassifyContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        mApplication = null;
        super.onDestroy();
    }

}

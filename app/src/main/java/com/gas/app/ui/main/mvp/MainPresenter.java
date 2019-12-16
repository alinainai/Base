package com.gas.app.ui.main.mvp;

import android.app.Application;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@ActivityScope
public class MainPresenter extends BasePresenter<IModel, MainContract.View> {

    @Inject
    Application mApplication;

    private Disposable mDisposable;

    @Inject
    public MainPresenter(MainContract.View rootView) {
        super(rootView);
    }

    @Override
    public void onDestroy() {

    }

}

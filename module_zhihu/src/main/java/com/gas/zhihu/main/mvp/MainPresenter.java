package com.gas.zhihu.main.mvp;

import android.app.Application;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    @Inject
    Application mApplication;



    @Inject
    public MainPresenter(MainContract.Model model,MainContract.View rootView) {
        super(model,rootView);
    }

    @Override
    public void onDestroy() {

    }




}

package com.gas.app.ui.fragment.mine.mvp;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;

@FragmentScope
public class MinePresenter extends BasePresenter<MineModle, MineContract.View> {


    @Inject
    Application mApplication;

    @Inject
    public MinePresenter(MineModle model, MineContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}

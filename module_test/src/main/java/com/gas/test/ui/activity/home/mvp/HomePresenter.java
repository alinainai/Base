package com.gas.test.ui.activity.home.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

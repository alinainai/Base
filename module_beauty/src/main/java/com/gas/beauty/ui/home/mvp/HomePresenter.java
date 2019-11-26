package com.gas.beauty.ui.home.mvp;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;

@ActivityScope
public class HomePresenter extends BasePresenter<IModel, HomeContract.View> {


    @Inject
    public HomePresenter(HomeContract.View rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy() {

    }


}

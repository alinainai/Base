package com.gas.test.ui.activity.adapter.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;


@ActivityScope
public class AdapterPresenter extends BasePresenter<IModel, AdapterContract.View> {

    @Inject
    public AdapterPresenter(AdapterContract.View rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

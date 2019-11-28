package com.gas.test.ui.activity.show.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;


@ActivityScope
public class ShowPresenter extends BasePresenter<IModel, ShowContract.View> {

    @Inject
    public ShowPresenter(ShowContract.View rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

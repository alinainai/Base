package com.gas.zhihu.ui.show.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@ActivityScope
public class ShowPresenter extends BasePresenter<ShowContract.Model, ShowContract.View> {

    @Inject
    public ShowPresenter(ShowContract.Model model, ShowContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

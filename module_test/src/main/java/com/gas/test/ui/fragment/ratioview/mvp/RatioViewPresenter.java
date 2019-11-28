package com.gas.test.ui.fragment.ratioview.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.mvp.IModel;

import javax.inject.Inject;


@FragmentScope
public class RatioViewPresenter extends BasePresenter<IModel, RatioViewContract.View> {

    @Inject
    public RatioViewPresenter(RatioViewContract.View rootView) {
        super(rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

package com.gas.test.ui.fragment.rxjava.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@FragmentScope
public class RxJavaPresenter extends BasePresenter<RxJavaContract.Model, RxJavaContract.View> {

    @Inject
    public RxJavaPresenter(RxJavaContract.Model model, RxJavaContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

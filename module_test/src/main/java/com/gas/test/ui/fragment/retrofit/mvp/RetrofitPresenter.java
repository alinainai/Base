package com.gas.test.ui.fragment.retrofit.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@FragmentScope
public class RetrofitPresenter extends BasePresenter<RetrofitContract.Model, RetrofitContract.View> {

    @Inject
    public RetrofitPresenter(RetrofitContract.Model model, RetrofitContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

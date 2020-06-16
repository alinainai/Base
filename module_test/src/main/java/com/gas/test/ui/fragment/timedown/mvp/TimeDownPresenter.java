package com.gas.test.ui.fragment.timedown.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@FragmentScope
public class TimeDownPresenter extends BasePresenter<TimeDownContract.Model, TimeDownContract.View> {

    @Inject
    public TimeDownPresenter(TimeDownContract.Model model, TimeDownContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

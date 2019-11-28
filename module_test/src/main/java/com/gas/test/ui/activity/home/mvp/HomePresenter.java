package com.gas.test.ui.activity.home.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.gas.test.bean.TestInfoBean;
import com.gas.test.ui.activity.home.HomeAdapter;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {


    @Inject
    HomeAdapter mAdapter;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void showTestInfos() {
        List<TestInfoBean> infos = mModel.getInfoItems();
        mAdapter.setNewData(infos);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

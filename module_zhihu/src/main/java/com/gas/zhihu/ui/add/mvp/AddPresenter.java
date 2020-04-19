package com.gas.zhihu.ui.add.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@ActivityScope
public class AddPresenter extends BasePresenter<AddContract.Model, AddContract.View> {

    @Inject
    public AddPresenter(AddContract.Model model, AddContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

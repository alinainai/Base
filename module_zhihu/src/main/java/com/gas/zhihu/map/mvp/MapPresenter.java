package com.gas.zhihu.map.mvp;


import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@ActivityScope
public class MapPresenter extends BasePresenter<MapContract.Model, MapContract.View> {

    @Inject
    public MapPresenter(MapContract.Model model, MapContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

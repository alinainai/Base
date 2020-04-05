package com.gas.zhihu.ui.show.mvp;


import android.text.TextUtils;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.gas.zhihu.bean.MapBean;
import com.lib.commonsdk.utils.GasAppUtils;
import com.lib.commonsdk.utils.TimeUtils;
import com.lib.commonsdk.utils.Utils;

import javax.inject.Inject;


@ActivityScope
public class ShowPresenter extends BasePresenter<ShowContract.Model, ShowContract.View> {


    private MapBean mMap;

    @Inject
    public ShowPresenter(ShowContract.Model model, ShowContract.View rootView) {
        super(model, rootView);
        mMap = model.getDefaultMapInfo();
        mMap.setLocationInfo("东直门");
    }


    public void setAddressToCopy(){
        if(!TextUtils.isEmpty(mMap.getLocationInfo())){
            Utils.copyData(GasAppUtils.getApp(),mMap.getLocationInfo());
            mView.showMessage("复制成功，请去地图软件搜索");
        }else {
            mView.showMessage("复制失败，地址信息为空");
        }
    }

    public void freshViewData(){

        mView.successView();
        mView.setDataInfo(mMap);
        mView.setQrCode(TimeUtils.getDateAndTime(TimeUtils.getNow()));

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

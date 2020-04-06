package com.gas.zhihu.ui.show.mvp;


import android.text.TextUtils;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.gas.zhihu.bean.LocationBean;
import com.gas.zhihu.bean.MapBean;
import com.lib.commonsdk.utils.GasAppUtils;
import com.lib.commonsdk.utils.TimeUtils;
import com.lib.commonsdk.utils.Utils;

import javax.inject.Inject;


@ActivityScope
public class ShowPresenter extends BasePresenter<ShowContract.Model, ShowContract.View> {


    private MapBean mMap;
    private String mQrCodeInfo;

    @Inject
    public ShowPresenter(ShowContract.Model model, ShowContract.View rootView) {
        super(model, rootView);
    }


    public MapBean queryDate(String keyName){
        mMap= mModel.getMapInfo(keyName);
        mQrCodeInfo=TimeUtils.getDateWithoutTime(TimeUtils.getNow());
        return mMap;
    }

    public void setAddressToCopy(){

        if(mMap!=null && !TextUtils.isEmpty(mMap.getLocationInfo())){
            Utils.copyData(GasAppUtils.getApp(),mMap.getLocationInfo());
            mView.showMessage("复制成功，请去地图软件搜索");
        }else {
            mView.showMessage("复制失败，地址信息为空");
        }
    }

    public void freshViewData(){

        mView.successView();
        mView.setDataInfo(mMap);
        mView.setQrCode(mQrCodeInfo);

    }

    public String getQrCodeInfo(){
        return mQrCodeInfo;
    }

    public MapBean getMapBeanInfo(){
        return mMap;
    }
    public LocationBean getLocationInfo(){
        if(mMap==null){
            return new LocationBean();
        }
        return mMap.getLocationBean();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

package com.gas.zhihu.ui.show.mvp

import android.text.TextUtils
import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.bean.LocationBean
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.utils.MapBeanDbUtils
import com.lib.commonsdk.utils.GasAppUtils
import com.lib.commonsdk.utils.TimeUtils
import com.lib.commonsdk.utils.Utils
import javax.inject.Inject

@ActivityScope
class ShowPresenter @Inject constructor(model: ShowContract.Model?, rootView: ShowContract.View?) : BasePresenter<ShowContract.Model?, ShowContract.View?>(model, rootView) {
    var mapBeanInfo: MapBean? = null
        private set
    var qrCodeInfo: String? = null
        private set

    fun queryDate(keyName: String?): MapBean? {
        mapBeanInfo = mModel!!.getMapInfo(keyName)
        qrCodeInfo = TimeUtils.getDateWithoutTime(TimeUtils.getNow())
        return mapBeanInfo
    }

    fun setAddressToCopy() {
        if (mapBeanInfo != null && !TextUtils.isEmpty(mapBeanInfo!!.locationInfo)) {
            Utils.copyData(GasAppUtils.getApp(), mapBeanInfo!!.locationInfo)
            mView!!.showMessage("复制成功，请去地图软件搜索")
        } else {
            mView!!.showMessage("复制失败，地址信息为空")
        }
    }

    fun freshViewData() {
        mView!!.successView()
        mView!!.setDataInfo(mapBeanInfo)
        mView!!.setQrCode(qrCodeInfo)
    }

    fun addComment(str:String){
        str.let {
            mapBeanInfo?.let {
                MapBeanDbUtils.addComment(mapBeanInfo?.keyName,str)
                updateComment();
            }
        }
    }

    fun updateComment(){
        mapBeanInfo = mModel!!.getMapInfo(mapBeanInfo?.keyName)
        mView?.setDataInfo(mapBeanInfo)
    }

    val locationInfo: LocationBean
        get() = if (mapBeanInfo == null) {
            LocationBean()
        } else mapBeanInfo!!.locationBean



    override fun onDestroy() {
        super.onDestroy()
    }
}
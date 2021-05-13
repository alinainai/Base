package com.gas.beauty.component.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.componentservice.gank.bean.GankInfo
import com.base.componentservice.gank.service.GankInfoService
import com.lib.commonsdk.constants.RouterHub


@Route(path = RouterHub.GANK_SERVICE_GANKINFOSERVICE, name = "干货信息服务")
class GankInfoServiceImpl : GankInfoService {
    private var mContext: Context? = null
    override fun getInfo(): GankInfo {
        return GankInfo("干货")
    }

    override fun init(context: Context) {
        mContext = context
    }
}
package com.gas.beauty.data.beauty

import com.gas.beauty.bean.BeautyBean
import com.gas.beauty.bean.GankResponse
import com.gas.beauty.data.BaseRemoteDataSource


internal class BeautyLocalDataSource {

}

internal class BeautyRemoteDataSource : BaseRemoteDataSource() {

    suspend fun getBeauties(): GankResponse<List<BeautyBean>> {
        return retrofitManager.obtainRetrofitService(BeautyApiService::class.java).getBeautyList(100,1)
    }

}

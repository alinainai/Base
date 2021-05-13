package com.gas.beauty.data.beauty

import com.gas.beauty.bean.BeautyBean
import com.gas.beauty.bean.GankResponse

interface BeautyDataRepo {
//    companion object {
//        @JvmStatic
//        val ins = BeautyDataRepoImpl()
//    }
    suspend fun getRemoteBeauties(): GankResponse<List<BeautyBean>>
}

class BeautyDataRepoImpl : BeautyDataRepo {
    private val remoteDataSource = BeautyRemoteDataSource()
    override suspend fun getRemoteBeauties(): GankResponse<List<BeautyBean>> {
        return remoteDataSource.getBeauties()
    }
}
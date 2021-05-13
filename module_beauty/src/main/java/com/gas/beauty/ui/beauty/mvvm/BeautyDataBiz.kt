package com.gas.beauty.ui.beauty.mvvm

import com.gas.beauty.bean.BeautyBean
import com.gas.beauty.bean.GankResponse
import com.gas.beauty.data.beauty.BeautyDataRepo
import com.gas.beauty.data.beauty.BeautyDataRepoImpl

/**
 * 界面如果包含多个 DataRepo，可以封装一个 DataBiz（作为 Business 层处理数据）
 */
class BeautyDataBiz {

    private val repo= BeautyDataRepoImpl()

    suspend fun getBeauties(): GankResponse<List<BeautyBean>> {
       return repo.getRemoteBeauties()
    }
}
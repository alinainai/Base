package com.gas.beauty.data.beauty

import com.gas.beauty.app.Api
import com.gas.beauty.bean.BeautyBean
import com.gas.beauty.bean.GankResponse
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BeautyApiService {

    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.GANK_DOMAIN_NAME)
    @GET("/api/data/福利/{num}/{page}")
    suspend fun getBeautyList(@Path("num") num: Int, @Path("page") page: Int): GankResponse<List<BeautyBean>>

}
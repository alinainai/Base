package com.gas.beauty.http

import com.gas.beauty.bean.GankBaseResponse
import com.gas.beauty.bean.GankItemBean
import io.reactivex.Observable
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GankService {
    /**
     * 最新日报
     */
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.GANK_DOMAIN_NAME)
    @GET("/api/data/福利/{num}/{page}")
    fun getGirlList(@Path("num") num: Int, @Path("page") page: Int): Observable<GankBaseResponse<List<GankItemBean>>>

    @GET("{suburl}")
    fun getGankItemData(@Path("suburl") suburl: String): Observable<GankBaseResponse<List<GankItemBean>>>
}
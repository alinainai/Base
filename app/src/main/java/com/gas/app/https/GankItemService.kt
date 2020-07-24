package com.gas.app.https

import com.gas.app.bean.GankItemData
import com.gas.app.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author: Othershe
 * Time: 2016/8/12 16:50
 */
interface GankItemService {
    @GET("{suburl}")
    fun getGankItemData(@Path("suburl") suburl: String?): Observable<HttpResult<List<GankItemData>>>
}
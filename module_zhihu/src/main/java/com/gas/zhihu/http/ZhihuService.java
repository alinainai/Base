package com.gas.zhihu.http;

import com.gas.zhihu.bean.DailyListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

import static com.gas.zhihu.http.Api.ZHIHU_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface ZhihuService {




    /**
     * 最新日报
     */
    @Headers({DOMAIN_NAME_HEADER + ZHIHU_DOMAIN_NAME})
    @GET("/api/4/news/latest")
    Observable<DailyListBean> getDailyList();


}

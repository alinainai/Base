package com.gas.zhihu.app;

import com.gas.zhihu.bean.DailyListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ZhihuService {


    String ZHIHU_DOMAIN = "http://news-at.zhihu.com/";


    /**
     * 最新日报
     */

    @GET("api/4/news/latest")
    Observable<DailyListBean> getDailyList(@Url String url);


}

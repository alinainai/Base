package com.test.module_beauty.http;

import com.test.module_beauty.bean.GankBaseResponse;
import com.test.module_beauty.bean.GankItemBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.test.module_beauty.http.Api.GANK_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface GankService {


    /**
     * 最新日报
     */
    @Headers({DOMAIN_NAME_HEADER + GANK_DOMAIN_NAME})
    @GET("/api/data/福利/{num}/{page}")
    Observable<GankBaseResponse<List<GankItemBean>>> getGirlList(@Path("num") int num, @Path("page") int page);


}

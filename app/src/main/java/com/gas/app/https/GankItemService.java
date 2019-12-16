package com.gas.app.https;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.gas.app.bean.GankItemData;
import com.gas.app.bean.HttpResult;

/**
 * Author: Othershe
 * Time: 2016/8/12 16:50
 */

public interface GankItemService {

    @GET("{suburl}")
    Observable<HttpResult<List<GankItemData>>> getGankItemData(@Path("suburl") String suburl);


}

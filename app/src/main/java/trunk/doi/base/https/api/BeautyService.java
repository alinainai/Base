package trunk.doi.base.https.api;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;

/**
 * 作者：Mr.Lee on 2016-11-9 15:19
 * 邮箱：569932357@qq.com
 */

public interface BeautyService {
    String BASE_URL = "http://gank.io/api/";
    @GET("{suburl}")
    Observable<BeautyResult<List<GankItemData>>> getBeautyData(@Path("suburl") String suburl);
}

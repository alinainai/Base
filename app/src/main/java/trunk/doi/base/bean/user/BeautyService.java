package trunk.doi.base.bean.user;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 作者：Mr.Lee on 2016-11-9 15:19
 * 邮箱：569932357@qq.com
 */

public interface BeautyService {
    String BASE_URL = "http://gank.io/api/";
    @GET("{suburl}")
    Observable<BeautyResult<List<Beauty>>> getGankBeautyData(@Path("suburl") String suburl);
}

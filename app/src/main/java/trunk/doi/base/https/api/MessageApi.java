package trunk.doi.base.https.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 作者：Mr.Lee on 2017-8-25 16:38
 * 邮箱：569932357@qq.com
 */

public interface MessageApi {

    String BASE_URL = "http://10.100.160.156:8080/";
    @GET("msg/sendMsg/{mobile}")
    Observable<Object> getMessageData(@Path("mobile") String mobile);

}

package trunk.doi.base.https;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;
import trunk.doi.base.bean.HttpResult;

/**
 * 作者：Mr.Lee on 2016-8-15 18:30
 * 邮箱：569932357@qq.com
 */
public class AppHttpApi {



    //测试信息
    public interface uploadIcon {
        String BASE_URL = "http://10.100.24.108:8080/mvc/";
        @Multipart
        @POST("user/updateicon.do")
        Observable<HttpResult<String>> getIconUrl(@Part("attach\";filename=\"icon.jpg") RequestBody file, @Part("username") RequestBody username);
    }

    public interface uploadLog {
        String BASE_URL = "http://10.100.24.122:8080/borrowApply/";
        @Multipart
        @POST("uploadAttach")
        Observable<HttpResult<Boolean>> uploadFilesWithParts(@Part() List<MultipartBody.Part> attachs);
    }


}

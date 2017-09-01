package trunk.doi.base.ui.fragment.classify;


import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.HttpException;
import rx.Subscriber;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.base.mvp.BasePresenter;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.rx.RxManager;
import trunk.doi.base.util.ToastUtils;


/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class GankItemPresenter extends BasePresenter<GankItemView> {
    private IGankItemModel mModel;

    public GankItemPresenter() {
        mModel = new GankItemModelImpl();
    }

    public void getGankItemData(String suburl) {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getGankItemData(suburl), new Subscriber<HttpResult<List<GankItemData>>>() {

            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable throwable) {

                if (throwable instanceof SocketTimeoutException) {
                    ToastUtils.showShort(BaseApplication.instance, "连接超时");
                } else if (throwable instanceof JsonSyntaxException) {
                    ToastUtils.showShort(BaseApplication.instance, "数据解析错误");
                } else if (throwable instanceof HttpException) {
                    ToastUtils.showShort(BaseApplication.instance, "连接异常");
                } else {
                    ToastUtils.showShort(BaseApplication.instance, "连接异常");
                }

                mView.onError();
            }

            @Override
            public void onNext(HttpResult<List<GankItemData>> gankItemDatas) {
                mView.onSuccess(gankItemDatas.getResults());
            }
        });
    }
}

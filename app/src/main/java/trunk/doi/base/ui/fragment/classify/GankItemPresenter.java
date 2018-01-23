package trunk.doi.base.ui.fragment.classify;


import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.HttpException;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.base.mvp.BasePresenter;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.rx.RxManager;
import trunk.doi.base.https.rx.RxSubscriber;
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
        RxManager.getInstance().doSubscribe(mModel.getGankItemData(suburl), new RxSubscriber<HttpResult<List<GankItemData>>>(false) {

            @Override
            protected void _onNext(HttpResult<List<GankItemData>> listHttpResult) {
                mView.onSuccess(listHttpResult.getResults());
            }

            @Override
            protected void _onError() {
                mView.onError();
            }
        });
    }
}

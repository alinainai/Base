package trunk.doi.base.ui.fragment.classify;


import java.util.List;

import rx.Subscriber;
import trunk.doi.base.base.mvp.BasePresenter;
import trunk.doi.base.https.HttpResult;
import trunk.doi.base.https.rx.RxManager;


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
            public void onError(Throwable e) {
                mView.onError();
            }

            @Override
            public void onNext(HttpResult<List<GankItemData>> gankItemDatas) {
                mView.onSuccess(gankItemDatas.getResults());
            }
        });
    }
}

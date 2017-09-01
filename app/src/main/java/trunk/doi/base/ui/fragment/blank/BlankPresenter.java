package trunk.doi.base.ui.fragment.blank;


import java.util.List;

import rx.Subscriber;
import trunk.doi.base.base.mvp.BasePresenter;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.https.rx.RxManager;


/**
 * Author: Othershe
 * Time: 2016/8/12 14:29
 */
public class BlankPresenter extends BasePresenter<BlankView> {
    private IBlankModel mModel;

    public BlankPresenter() {
        mModel = new BlankModelImpl();
    }

    public void getItemData(String suburl) {
        mSubscription = RxManager.getInstance().doSubscribe(mModel.getGankItemData(suburl), new Subscriber<BeautyResult<List<GankItemData>>>() {

            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                mView.onError();
            }

            @Override
            public void onNext(BeautyResult<List<GankItemData>> gankItemDatas) {
                mView.onSuccess(gankItemDatas.getResults());
            }
        });
    }
}

package trunk.doi.base.ui.fragment.blank;


import java.util.List;

import trunk.doi.base.base.mvp.BasePresenter;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.https.rx.RxManager;
import trunk.doi.base.https.rx.RxSubscriber;


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
        RxManager.getInstance().doSubscribe(mModel.getGankItemData(suburl), new RxSubscriber<BeautyResult<List<GankItemData>>>(false) {
            @Override
            protected void _onNext(BeautyResult<List<GankItemData>> gankItemData) {
                mView.onSuccess(gankItemData.getResults());
            }

            @Override
            protected void _onError() {

            }

//            @Override
//            public void onComplete() {
//
//            }
//            @Override
//            public void onError(Throwable e) {
//                mView.onError();
//            }
//
//            @Override
//            public void onNext(BeautyResult<List<GankItemData>> gankItemDatas) {
//                mView.onSuccess(gankItemDatas.getResults());
//            }
        });
    }
}

package com.gas.beauty.ui.classify.mvp;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.beauty.bean.GankItemBean;
import com.gas.beauty.ui.classify.ClassifyAdapter;
import com.lib.commonsdk.rx.RxBindManager;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.gas.beauty.app.GankConstants.NUMBER_OF_PAGE;

@FragmentScope
public class ClassifyPresenter extends BasePresenter<ClassifyModle, ClassifyContract.View> {


    @Inject
    Application mApplication;

    @Inject
    ClassifyAdapter mClassifyAdapter;

    private int mPage = 1;//页数


    @Inject
    public ClassifyPresenter(ClassifyModle model, ClassifyContract.View rootView) {
        super(model, rootView);
    }

    public void getGankItemData(String suburl, boolean refresh) {

        if (refresh) {
            mPage = 1;
        }

        RxBindManager.getInstance().doSubscribe(mModel.getGankItemData(String.format(Locale.CHINA, "data/%s/" + NUMBER_OF_PAGE + "/%d", suburl, mPage)),
                new Observer<List<GankItemBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GankItemBean> result) {

                        mView.loadEnd();

                        if (null != result && result.size() > 0) {

                            if (mPage == 1) {
                                mClassifyAdapter.setNewData(result);
                            } else {
                                mClassifyAdapter.setLoadMoreData(result);
                            }
                            if (result.size() < NUMBER_OF_PAGE) {//如果小于10个
                                mClassifyAdapter.loadEnd();
                            }
                            mPage++;
                        } else {
                            mView.loadEnd();
                            if (mPage > 1) {
                                mClassifyAdapter.showFooterFail();
                            } else {
                                mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_FAIL);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mPage > 1) {
                            mClassifyAdapter.showFooterFail();
                        } else {
                            mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_FAIL);
                        }
                        mView.loadEnd();
                    }

                    @Override
                    public void onComplete() {

                    }
                }, mView);
    }

    @Override
    public void onDestroy() {
        mApplication = null;
        super.onDestroy();
    }

}

package com.gas.beauty.ui.article.classify.mvp;

import android.app.Application;

import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;
import com.gas.beauty.bean.GankItemBean;
import com.lib.commonsdk.rx.RxBindManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@FragmentScope
public class ClassifyPresenter extends BasePresenter<ClassifyModle, ClassifyContract.View> {


    @Inject
    Application mApplication;


    @Inject
    public ClassifyPresenter(ClassifyModle model, ClassifyContract.View rootView) {
        super(model, rootView);
    }

    public void getGankItemData(String suburl) {
        RxBindManager.getInstance().doSubscribe(mModel.getGankItemData(suburl),
                new Observer<List<GankItemBean>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GankItemBean> result) {

                        if (null == result || result.size() == 0) {
                            mView.onError();
                            return;
                        }
                        mView.onSuccess(result);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError();
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

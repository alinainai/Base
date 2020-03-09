package com.gas.zhihu.main.mvp;

import android.app.Application;

import androidx.recyclerview.widget.RecyclerView;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.paginate.interfaces.EmptyInterface;
import com.lib.commonsdk.rx.RxBindManager;
import com.base.paginate.base.BaseAdapter;
import com.gas.zhihu.bean.DailyListBean;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    @Inject
    Application mApplication;


    @Inject
    RecyclerView.Adapter mAdapter;


    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    public void requestDailyList() {
        RxBindManager.getInstance().doSubscribe(mModel.getDailyList(),
                new Observer<DailyListBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DailyListBean result) {
                        Timber.e("请求成功success");
                        mView.success();
                        if (null != result&& null!= result.getStories()&&!result.getStories().isEmpty()) {
                            ((BaseAdapter) mAdapter).setNewData(result.getStories());
                            if (result.getStories().size() < 10) {//如果小于10个
                                ((BaseAdapter) mAdapter).loadEnd();
                            }
                        }else {
                            ((BaseAdapter) mAdapter).loadEnd();
                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.toString());
                        mView.onError();
                    }

                    @Override
                    public void onComplete() {
                        Timber.e("请求onComplete");
                    }
                }, mView);


    }

    @Override
    public void onDestroy() {

    }


}

package com.gas.zhihu.main.mvp;

import android.app.Application;

import androidx.recyclerview.widget.RecyclerView;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.rx.RxBindManager;
import com.base.paginate.base.BaseAdapter;
import com.gas.zhihu.bean.DailyListBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    @Inject
    Application mApplication;

    @Inject
    List<DailyListBean.StoriesBean> mDatas;
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
                        if (null != result) {
                            mDatas.clear();
                            mDatas.addAll(result.getStories());
                            ((BaseAdapter) mAdapter).setNewData(mDatas);
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

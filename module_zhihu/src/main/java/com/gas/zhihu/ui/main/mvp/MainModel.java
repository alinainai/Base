package com.gas.zhihu.ui.main.mvp;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;
import com.gas.zhihu.http.ZhihuService;
import com.gas.zhihu.bean.DailyListBean;
import com.gas.zhihu.utils.MapBeanDbUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {


    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DailyListBean> getDailyList() {
        return mRepositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyList();
    }

    @Override
    public int getMapDataCount(){
        return MapBeanDbUtils.getMapDataCount();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }


}

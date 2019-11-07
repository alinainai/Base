package com.gas.zhihu.main.mvp;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.https.IRepositoryManager;
import com.base.lib.mvp.BaseModel;
import com.gas.zhihu.app.ZhihuService;
import com.gas.zhihu.bean.DailyListBean;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {


    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DailyListBean> getDailyList() {
        return mRepositoryManager.obtainRetrofitService(ZhihuService.class)
                .getDailyList(ZhihuService.ZHIHU_DOMAIN);
    }


}

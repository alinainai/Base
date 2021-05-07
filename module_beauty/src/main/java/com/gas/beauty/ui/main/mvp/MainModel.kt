package com.gas.beauty.ui.main.mvp;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;
import com.gas.beauty.bean.GankBaseResponse;
import com.gas.beauty.bean.GankItemBean;
import com.gas.beauty.http.GankService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {


    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<GankBaseResponse<List<GankItemBean>>> getGirlList(int num, int page) {
        return mRepositoryManager.obtainRetrofitService(GankService.class)
                .getGirlList(num,page);
    }
}

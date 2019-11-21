package com.test.module_beauty.main.mvp;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;
import com.test.module_beauty.bean.GankBaseResponse;
import com.test.module_beauty.bean.GankItemBean;
import com.test.module_beauty.http.GankService;

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

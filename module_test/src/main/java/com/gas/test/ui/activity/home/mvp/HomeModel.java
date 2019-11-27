package com.gas.test.ui.activity.home.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.ActivityScope;

import javax.inject.Inject;

import com.gas.test.ui.activity.home.mvp.HomeContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */

@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
package com.gas.test.ui.fragment.retrofit.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.FragmentScope;

import javax.inject.Inject;

import com.gas.test.ui.fragment.retrofit.mvp.RetrofitContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/05/2019 19:55
 * ================================================
 */

@FragmentScope
public class RetrofitModel extends BaseModel implements RetrofitContract.Model {

    @Inject
    public RetrofitModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
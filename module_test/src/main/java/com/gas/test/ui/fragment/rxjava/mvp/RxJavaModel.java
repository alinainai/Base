package com.gas.test.ui.fragment.rxjava.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.FragmentScope;

import javax.inject.Inject;

import com.gas.test.ui.fragment.rxjava.mvp.RxJavaContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/08/2019 15:56
 * ================================================
 */

@FragmentScope
public class RxJavaModel extends BaseModel implements RxJavaContract.Model {

    @Inject
    public RxJavaModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
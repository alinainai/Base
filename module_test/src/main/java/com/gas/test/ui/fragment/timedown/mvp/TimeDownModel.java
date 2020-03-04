package com.gas.test.ui.fragment.timedown.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.FragmentScope;

import javax.inject.Inject;

import com.gas.test.ui.fragment.timedown.mvp.TimeDownContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 03/04/2020 17:07
 * ================================================
 */

@FragmentScope
public class TimeDownModel extends BaseModel implements TimeDownContract.Model {

    @Inject
    public TimeDownModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
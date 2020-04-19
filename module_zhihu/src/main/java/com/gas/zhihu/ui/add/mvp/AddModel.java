package com.gas.zhihu.ui.add.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.ActivityScope;

import javax.inject.Inject;

import com.gas.zhihu.ui.add.mvp.AddContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 04/19/2020 09:24
 * ================================================
 */

@ActivityScope
public class AddModel extends BaseModel implements AddContract.Model {

    @Inject
    public AddModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
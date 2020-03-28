package com.gas.zhihu.map.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.ActivityScope;

import javax.inject.Inject;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 21:09
 * ================================================
 */

@ActivityScope
public class MapModel extends BaseModel implements MapContract.Model {

    @Inject
    public MapModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
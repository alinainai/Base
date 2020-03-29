package com.gas.zhihu.ui.show.mvp;


import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.ActivityScope;

import javax.inject.Inject;

import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.ui.show.mvp.ShowContract;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */

@ActivityScope
public class ShowModel extends BaseModel implements ShowContract.Model {

    @Inject
    public ShowModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public MapBean getMapInfo(String key) {
        return null;
    }

    @Override
    public MapBean getDefaultMapInfo() {
        return new MapBean();
    }
}
package com.gas.app.ui.activity.main.mvp

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import com.base.lib.di.scope.ActivityScope;
import javax.inject.Inject

import com.gas.app.ui.activity.main.mvp.MainContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:05
 * ================================================
 */
@ActivityScope
class MainModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {

    override fun onDestroy() {
        super.onDestroy();
    }
}

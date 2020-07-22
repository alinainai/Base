package com.gas.app.ui.activity.newsplash.mvp

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import com.base.lib.di.scope.ActivityScope;
import javax.inject.Inject

import com.gas.app.ui.activity.newsplash.mvp.SplashContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:29
 * ================================================
 */
@ActivityScope
class SplashModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SplashContract.Model {

    override fun onDestroy() {
        super.onDestroy();
    }
}

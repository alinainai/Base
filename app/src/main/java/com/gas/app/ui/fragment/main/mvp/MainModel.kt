package com.gas.app.ui.fragment.main.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import javax.inject.Inject

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
@FragmentScope
class MainModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {
    override fun onDestroy() {
        super.onDestroy()
    }
}
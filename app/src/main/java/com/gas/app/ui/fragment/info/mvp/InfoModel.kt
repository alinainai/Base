package com.gas.app.ui.fragment.info.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import javax.inject.Inject

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */
@FragmentScope
class InfoModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), InfoContract.Model {
    override fun onDestroy() {
        super.onDestroy()
    }
}
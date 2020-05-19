package com.gas.zhihu.ui.office.mvp

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import com.base.lib.di.scope.ActivityScope;
import javax.inject.Inject

import com.gas.zhihu.ui.office.mvp.OfficeContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 05/17/2020 10:42
 * ================================================
 */
@ActivityScope
class OfficeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), OfficeContract.Model {

    override fun onDestroy() {
        super.onDestroy();
    }
}

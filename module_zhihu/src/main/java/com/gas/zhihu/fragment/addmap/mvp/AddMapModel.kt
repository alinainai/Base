package com.gas.zhihu.fragment.addmap.mvp

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import com.base.lib.di.scope.FragmentScope;
import javax.inject.Inject

import com.gas.zhihu.fragment.addmap.mvp.AddMapContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:44
 * ================================================
 */
@FragmentScope
class AddMapModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AddMapContract.Model {

    override fun onDestroy() {
        super.onDestroy();
    }
}

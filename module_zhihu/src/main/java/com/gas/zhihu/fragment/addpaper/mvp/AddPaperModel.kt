package com.gas.zhihu.fragment.addpaper.mvp

import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel

import com.base.lib.di.scope.FragmentScope;
import javax.inject.Inject

import com.gas.zhihu.fragment.addpaper.mvp.AddPaperContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:45
 * ================================================
 */
@FragmentScope
class AddPaperModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), AddPaperContract.Model {

    override fun onDestroy() {
        super.onDestroy();
    }
}

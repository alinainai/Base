package com.gas.app.ui.fragment.mapshow.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import javax.inject.Inject

@FragmentScope
class MapShowModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MapShowContract.Model {

    override fun onDestroy() {
        super.onDestroy()
    }
}

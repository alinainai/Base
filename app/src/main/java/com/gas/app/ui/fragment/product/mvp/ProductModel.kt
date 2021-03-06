package com.gas.app.ui.fragment.product.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.integration.repository.IRepositoryManager
import com.base.lib.mvp.BaseModel
import javax.inject.Inject

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */
@FragmentScope
class ProductModel @Inject constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ProductContract.Model {
    override fun onDestroy() {
        super.onDestroy()
    }
}
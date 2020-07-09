package com.gas.app.ui.fragment.product.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import javax.inject.Inject

@FragmentScope
class ProductPresenter @Inject constructor(model: ProductContract.Model, rootView: ProductContract.View) : BasePresenter<ProductContract.Model, ProductContract.View>(model, rootView) {
    override fun onDestroy() {
        super.onDestroy()
    }
}
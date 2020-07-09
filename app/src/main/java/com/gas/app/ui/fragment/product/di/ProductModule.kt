package com.gas.app.ui.fragment.product.di

import com.gas.app.ui.fragment.product.mvp.ProductContract
import com.gas.app.ui.fragment.product.mvp.ProductModel
import dagger.Binds
import dagger.Module

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */
@Module
abstract class ProductModule {
    @Binds
    abstract fun bindProductModel(model: ProductModel): ProductContract.Model
}
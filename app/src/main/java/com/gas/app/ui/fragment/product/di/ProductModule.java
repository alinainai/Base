package com.gas.app.ui.fragment.product.di;

import dagger.Binds;
import dagger.Module;

import com.gas.app.ui.fragment.product.mvp.ProductContract;
import com.gas.app.ui.fragment.product.mvp.ProductModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */
@Module
public abstract class ProductModule {

    @Binds
    abstract ProductContract.Model bindProductModel(ProductModel model);


}
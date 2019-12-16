package com.gas.app.ui.fragment.product.mvp;


import com.base.lib.di.scope.FragmentScope;
import com.base.lib.mvp.BasePresenter;

import javax.inject.Inject;


@FragmentScope
public class ProductPresenter extends BasePresenter<ProductContract.Model, ProductContract.View> {

    @Inject
    public ProductPresenter(ProductContract.Model model, ProductContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

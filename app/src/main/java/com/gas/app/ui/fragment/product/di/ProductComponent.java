package com.gas.app.ui.fragment.product.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import com.gas.app.ui.fragment.product.mvp.ProductContract;
import com.gas.app.ui.fragment.product.ProductFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */

@FragmentScope
@Component(modules = ProductModule.class, dependencies = AppComponent.class)
public interface ProductComponent {

    void inject(ProductFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        ProductComponent.Builder view(ProductContract.View view);

        ProductComponent.Builder appComponent(AppComponent appComponent);

        ProductComponent build();

    }
}
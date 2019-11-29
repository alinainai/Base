package com.gas.test.ui.fragment.adapter.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import com.gas.test.ui.fragment.adapter.di.AdapterModule;
import com.gas.test.ui.fragment.adapter.mvp.AdapterContract;
import com.gas.test.ui.fragment.adapter.AdapterFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/29/2019 16:48
 * ================================================
 */

@FragmentScope
@Component(modules = AdapterModule.class, dependencies = AppComponent.class)
public interface AdapterComponent {

    void inject(AdapterFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        AdapterComponent.Builder view(AdapterContract.View view);

        AdapterComponent.Builder appComponent(AppComponent appComponent);

        AdapterComponent build();

    }
}
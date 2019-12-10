package com.gas.test.ui.fragment.retrofit.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import com.gas.test.ui.fragment.retrofit.di.RetrofitModule;
import com.gas.test.ui.fragment.retrofit.mvp.RetrofitContract;
import com.gas.test.ui.fragment.retrofit.RetrofitFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/05/2019 19:55
 * ================================================
 */

@FragmentScope
@Component(modules = RetrofitModule.class, dependencies = AppComponent.class)
public interface RetrofitComponent {

    void inject(RetrofitFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        RetrofitComponent.Builder view(RetrofitContract.View view);

        RetrofitComponent.Builder appComponent(AppComponent appComponent);

        RetrofitComponent build();

    }
}
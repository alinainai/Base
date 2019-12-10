package com.gas.test.ui.fragment.rxjava.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import com.gas.test.ui.fragment.rxjava.di.RxJavaModule;
import com.gas.test.ui.fragment.rxjava.mvp.RxJavaContract;
import com.gas.test.ui.fragment.rxjava.RxJavaFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/08/2019 15:56
 * ================================================
 */

@FragmentScope
@Component(modules = RxJavaModule.class, dependencies = AppComponent.class)
public interface RxJavaComponent {

    void inject(RxJavaFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        RxJavaComponent.Builder view(RxJavaContract.View view);

        RxJavaComponent.Builder appComponent(AppComponent appComponent);

        RxJavaComponent build();

    }
}
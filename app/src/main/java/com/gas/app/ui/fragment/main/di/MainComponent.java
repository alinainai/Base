package com.gas.app.ui.fragment.main.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import com.gas.app.ui.fragment.main.mvp.MainContract;
import com.gas.app.ui.fragment.main.MainFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */

@FragmentScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        MainComponent.Builder appComponent(AppComponent appComponent);

        MainComponent build();

    }
}
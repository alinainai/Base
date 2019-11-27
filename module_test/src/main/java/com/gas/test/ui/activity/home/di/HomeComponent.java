package com.gas.test.ui.activity.home.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.ActivityScope;

import com.gas.test.ui.activity.home.di.HomeModule;
import com.gas.test.ui.activity.home.mvp.HomeContract;
import com.gas.test.ui.activity.home.HomeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */

@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {

    void inject(HomeActivity activity);


    @Component.Builder
    interface Builder {

        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);

        HomeComponent.Builder appComponent(AppComponent appComponent);

        HomeComponent build();

    }
}
package com.gas.zhihu.ui.activity.login.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.ActivityScope;

import com.gas.zhihu.ui.activity.login.mvp.LoginContract;
import com.gas.zhihu.ui.activity.login.LoginActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 20:50
 * ================================================
 */

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);


    @Component.Builder
    interface Builder {

        @BindsInstance
        LoginComponent.Builder view(LoginContract.View view);

        LoginComponent.Builder appComponent(AppComponent appComponent);

        LoginComponent build();

    }
}
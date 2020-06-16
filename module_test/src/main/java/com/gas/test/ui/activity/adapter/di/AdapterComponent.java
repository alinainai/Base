package com.gas.test.ui.activity.adapter.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.ActivityScope;

import com.gas.test.ui.activity.adapter.mvp.AdapterContract;
import com.gas.test.ui.activity.adapter.AdapterActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/09/2020 09:59
 * ================================================
 */

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface AdapterComponent {

    void inject(AdapterActivity activity);


    @Component.Builder
    interface Builder {

        @BindsInstance
        AdapterComponent.Builder view(AdapterContract.View view);

        AdapterComponent.Builder appComponent(AppComponent appComponent);

        AdapterComponent build();

    }
}
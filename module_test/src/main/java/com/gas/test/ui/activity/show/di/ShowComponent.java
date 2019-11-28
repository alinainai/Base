package com.gas.test.ui.activity.show.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.ActivityScope;

import com.gas.test.ui.activity.show.di.ShowModule;
import com.gas.test.ui.activity.show.mvp.ShowContract;
import com.gas.test.ui.activity.show.ShowActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/28/2019 09:50
 * ================================================
 */

@ActivityScope
@Component(modules = ShowModule.class, dependencies = AppComponent.class)
public interface ShowComponent {

    void inject(ShowActivity activity);


    @Component.Builder
    interface Builder {

        @BindsInstance
        ShowComponent.Builder view(ShowContract.View view);

        ShowComponent.Builder appComponent(AppComponent appComponent);

        ShowComponent build();

    }
}
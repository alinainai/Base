package com.gas.zhihu.ui.show.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.ActivityScope;

import com.gas.zhihu.ui.show.di.ShowModule;
import com.gas.zhihu.ui.show.mvp.ShowContract;
import com.gas.zhihu.ui.show.ShowActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/28/2020 21:18
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
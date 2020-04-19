package com.gas.zhihu.ui.add.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.ActivityScope;

import com.gas.zhihu.ui.add.di.AddModule;
import com.gas.zhihu.ui.add.mvp.AddContract;
import com.gas.zhihu.ui.add.AddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 04/19/2020 09:24
 * ================================================
 */

@ActivityScope
@Component(modules = AddModule.class, dependencies = AppComponent.class)
public interface AddComponent {

    void inject(AddActivity activity);


    @Component.Builder
    interface Builder {

        @BindsInstance
        AddComponent.Builder view(AddContract.View view);

        AddComponent.Builder appComponent(AppComponent appComponent);

        AddComponent build();

    }
}
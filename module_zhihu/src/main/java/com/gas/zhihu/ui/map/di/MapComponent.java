package com.gas.zhihu.ui.map.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.ActivityScope;

import com.gas.zhihu.ui.map.mvp.MapContract;
import com.gas.zhihu.ui.map.MapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/24/2020 21:09
 * ================================================
 */

@ActivityScope
@Component(modules = MapModule.class, dependencies = AppComponent.class)
public interface MapComponent {

    void inject(MapActivity activity);


    @Component.Builder
    interface Builder {

        @BindsInstance
        MapComponent.Builder view(MapContract.View view);

        MapComponent.Builder appComponent(AppComponent appComponent);

        MapComponent build();

    }
}
package com.gas.test.ui.fragment.timedown.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import com.gas.test.ui.fragment.timedown.di.TimeDownModule;
import com.gas.test.ui.fragment.timedown.mvp.TimeDownContract;
import com.gas.test.ui.fragment.timedown.TimeDownFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 03/04/2020 17:07
 * ================================================
 */

@FragmentScope
@Component(modules = TimeDownModule.class, dependencies = AppComponent.class)
public interface TimeDownComponent {

    void inject(TimeDownFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        TimeDownComponent.Builder view(TimeDownContract.View view);

        TimeDownComponent.Builder appComponent(AppComponent appComponent);

        TimeDownComponent build();

    }
}
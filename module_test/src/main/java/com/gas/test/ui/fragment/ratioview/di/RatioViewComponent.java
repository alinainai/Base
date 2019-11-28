package com.gas.test.ui.fragment.ratioview.di;

import dagger.BindsInstance;
import dagger.Component;

import com.base.lib.di.component.AppComponent;
import com.base.lib.di.scope.FragmentScope;

import com.gas.test.ui.fragment.ratioview.mvp.RatioViewContract;
import com.gas.test.ui.fragment.ratioview.RatioViewFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/28/2019 09:58
 * ================================================
 */

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface RatioViewComponent {

    void inject(RatioViewFragment fragment);


    @Component.Builder
    interface Builder {

        @BindsInstance
        RatioViewComponent.Builder view(RatioViewContract.View view);

        RatioViewComponent.Builder appComponent(AppComponent appComponent);

        RatioViewComponent build();

    }
}
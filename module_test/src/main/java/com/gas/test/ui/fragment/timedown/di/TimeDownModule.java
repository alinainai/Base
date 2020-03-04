package com.gas.test.ui.fragment.timedown.di;

import com.base.lib.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.gas.test.ui.fragment.timedown.mvp.TimeDownContract;
import com.gas.test.ui.fragment.timedown.mvp.TimeDownModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 03/04/2020 17:07
 * ================================================
 */
@Module
public abstract class TimeDownModule {

    @Binds
    abstract TimeDownContract.Model bindTimeDownModel(TimeDownModel model);


}
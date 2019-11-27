package com.gas.test.ui.activity.home.di;

import com.base.lib.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.gas.test.ui.activity.home.mvp.HomeContract;
import com.gas.test.ui.activity.home.mvp.HomeModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */
@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);


}
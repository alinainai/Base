package com.gas.test.ui.fragment.retrofit.di;

import com.base.lib.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.gas.test.ui.fragment.retrofit.mvp.RetrofitContract;
import com.gas.test.ui.fragment.retrofit.mvp.RetrofitModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/05/2019 19:55
 * ================================================
 */
@Module
public abstract class RetrofitModule {

    @Binds
    abstract RetrofitContract.Model bindRetrofitModel(RetrofitModel model);


}
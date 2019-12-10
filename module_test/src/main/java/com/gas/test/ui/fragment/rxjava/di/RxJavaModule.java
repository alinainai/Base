package com.gas.test.ui.fragment.rxjava.di;

import com.base.lib.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.gas.test.ui.fragment.rxjava.mvp.RxJavaContract;
import com.gas.test.ui.fragment.rxjava.mvp.RxJavaModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/08/2019 15:56
 * ================================================
 */
@Module
public abstract class RxJavaModule {

    @Binds
    abstract RxJavaContract.Model bindRxJavaModel(RxJavaModel model);


}
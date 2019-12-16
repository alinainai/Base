package com.gas.app.ui.fragment.main.di;

import dagger.Binds;
import dagger.Module;

import com.gas.app.ui.fragment.main.mvp.MainContract;
import com.gas.app.ui.fragment.main.mvp.MainModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);


}
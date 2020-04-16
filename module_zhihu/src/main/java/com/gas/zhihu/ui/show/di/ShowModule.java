package com.gas.zhihu.ui.show.di;

import com.gas.zhihu.ui.show.mvp.ShowContract;
import com.gas.zhihu.ui.show.mvp.ShowModel;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class ShowModule {
    @Binds
    abstract ShowContract.Model bindMaapModel(ShowModel model);
}

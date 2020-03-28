package com.gas.zhihu.ui.show.di;

import com.base.lib.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.gas.zhihu.ui.show.mvp.ShowContract;
import com.gas.zhihu.ui.show.mvp.ShowModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */
@Module
public abstract class ShowModule {

    @Binds
    abstract ShowContract.Model bindShowModel(ShowModel model);


}
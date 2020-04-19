package com.gas.zhihu.ui.add.di;

import com.base.lib.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.gas.zhihu.ui.add.mvp.AddContract;
import com.gas.zhihu.ui.add.mvp.AddModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 04/19/2020 09:24
 * ================================================
 */
@Module
public abstract class AddModule {

    @Binds
    abstract AddContract.Model bindAddModel(AddModel model);


}
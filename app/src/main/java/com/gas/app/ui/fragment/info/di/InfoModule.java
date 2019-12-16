package com.gas.app.ui.fragment.info.di;

import dagger.Binds;
import dagger.Module;

import com.gas.app.ui.fragment.info.mvp.InfoContract;
import com.gas.app.ui.fragment.info.mvp.InfoModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */
@Module
public abstract class InfoModule {

    @Binds
    abstract InfoContract.Model bindInfoModel(InfoModel model);


}
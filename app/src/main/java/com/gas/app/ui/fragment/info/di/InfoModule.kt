package com.gas.app.ui.fragment.info.di

import com.gas.app.ui.fragment.info.mvp.InfoContract
import com.gas.app.ui.fragment.info.mvp.InfoModel
import dagger.Binds
import dagger.Module

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:54
 * ================================================
 */
@Module
abstract class InfoModule {
    @Binds
    abstract fun bindInfoModel(model: InfoModel): InfoContract.Model
}
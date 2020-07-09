package com.gas.app.ui.fragment.main.di

import com.gas.app.ui.fragment.main.mvp.MainContract
import com.gas.app.ui.fragment.main.mvp.MainModel
import dagger.Binds
import dagger.Module

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
@Module
abstract class MainModule {
    @Binds
    abstract fun bindMainModel(model: MainModel): MainContract.Model
}
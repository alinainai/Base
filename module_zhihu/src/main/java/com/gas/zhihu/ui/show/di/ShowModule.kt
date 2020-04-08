package com.gas.zhihu.ui.show.di

import com.gas.zhihu.ui.show.mvp.ShowContract
import com.gas.zhihu.ui.show.mvp.ShowModel
import dagger.Binds
import dagger.Module

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpTemplate on 03/28/2020 21:18
 * ================================================
 */
@Module
abstract class ShowModule {
    @Binds
    abstract fun bindShowModel(model: ShowModel?): ShowContract.Model?
}
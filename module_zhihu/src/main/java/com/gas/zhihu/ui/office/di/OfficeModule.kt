package com.gas.zhihu.ui.office.di

import com.base.lib.di.scope.ActivityScope

import dagger.Module
import dagger.Provides
import com.gas.zhihu.ui.office.mvp.OfficeContract
import com.gas.zhihu.ui.office.mvp.OfficeModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 05/17/2020 10:42
 * ================================================
 */
@Module
class OfficeModule(private val view: OfficeContract.View) {


    @ActivityScope
    @Provides
    fun provideOfficeModel(model: OfficeModel): OfficeContract.Model {
        return model
    }


    @ActivityScope
    @Provides
    fun provideOfficeView(): OfficeContract.View {
        return this.view
    }

}

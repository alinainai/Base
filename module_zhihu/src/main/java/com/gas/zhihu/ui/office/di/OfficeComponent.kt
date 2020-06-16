package com.gas.zhihu.ui.office.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.ActivityScope

import com.gas.zhihu.ui.office.di.OfficeModule

import com.gas.zhihu.ui.office.OfficeActivity

import com.gas.zhihu.ui.office.mvp.OfficeContract


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 05/17/2020 10:42
 * ================================================
 */

@ActivityScope
@Component(modules = arrayOf(OfficeModule::class), dependencies = arrayOf(AppComponent::class))
interface OfficeComponent {

    fun inject(activity: OfficeActivity)

}

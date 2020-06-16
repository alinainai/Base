package com.gas.zhihu.fragment.addpaper.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.addpaper.di.AddPaperModule
import com.gas.zhihu.fragment.addpaper.mvp.AddPaperContract
import com.gas.zhihu.fragment.addpaper.AddPaperFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:45
 * ================================================
 */

@FragmentScope
@Component(modules = arrayOf(AddPaperModule::class), dependencies = arrayOf(AppComponent::class))
interface AddPaperComponent {

    fun inject(fragment: AddPaperFragment)


}

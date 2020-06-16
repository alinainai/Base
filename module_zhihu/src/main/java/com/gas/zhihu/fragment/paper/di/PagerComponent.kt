package com.gas.zhihu.fragment.paper.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.paper.di.PagerModule
import com.gas.zhihu.fragment.paper.mvp.PagerContract
import com.gas.zhihu.fragment.paper.PagerFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */

@FragmentScope
@Component(modules = arrayOf(PagerModule::class), dependencies = arrayOf(AppComponent::class))
interface PagerComponent {

    fun inject(fragment: PagerFragment)


}

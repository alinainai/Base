package com.gas.zhihu.fragment.papersearch.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.papersearch.di.PaperSearchModule
import com.gas.zhihu.fragment.papersearch.mvp.PaperSearchContract
import com.gas.zhihu.fragment.papersearch.PaperSearchFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 06/20/2020 17:46
 * ================================================
 */

@FragmentScope
@Component(modules = arrayOf(PaperSearchModule::class), dependencies = arrayOf(AppComponent::class))
interface PaperSearchComponent {

    fun inject(fragment: PaperSearchFragment)


}

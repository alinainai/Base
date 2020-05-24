package com.gas.zhihu.fragment.fileselect.di

import dagger.Component
import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.fileselect.di.FileSelectModule
import com.gas.zhihu.fragment.fileselect.mvp.FileSelectContract
import com.gas.zhihu.fragment.fileselect.FileSelectFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/24/2020 11:32
 * ================================================
 */

@FragmentScope
@Component(modules = arrayOf(FileSelectModule::class), dependencies = arrayOf(AppComponent::class))
interface FileSelectComponent {

    fun inject(fragment: FileSelectFragment)


}

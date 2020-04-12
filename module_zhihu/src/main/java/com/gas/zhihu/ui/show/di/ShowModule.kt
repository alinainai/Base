package com.gas.zhihu.ui.show.di

import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.ActivityScope
import com.base.lib.https.image.ImageLoader
import com.base.lib.util.ArmsUtils
import com.gas.zhihu.ui.show.mvp.ShowContract
import com.gas.zhihu.ui.show.mvp.ShowModel
import dagger.Binds
import dagger.Module
import dagger.Provides

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

    open fun getImageLoader(appComponent: AppComponent):ImageLoader{
        return appComponent.imageLoader()
    }
}
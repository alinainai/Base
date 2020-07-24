package com.gas.app.ui.activity.main.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.base.lib.di.scope.ActivityScope
import com.gas.app.ui.activity.main.mvp.MainContract
import com.gas.app.ui.activity.main.mvp.MainModel
import com.gas.app.ui.fragment.info.InfoFragment
import com.gas.app.ui.fragment.main.MainFragment
import com.gas.app.ui.fragment.mine.MineFragment
import com.gas.app.ui.fragment.product.ProductFragment
import com.lib.commonsdk.adapter.AdapterViewPager
import dagger.Module
import dagger.Provides
import java.util.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:05
 * ================================================
 */
@Module
//构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MainModule(private val view: MainContract.View) {

    @ActivityScope
    @Provides
    fun provideMainView(): MainContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMainModel(model: MainModel): MainContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun providePagerAdapter(): AdapterViewPager {
        val fragments = listOf<Fragment>(MainFragment.newInstance(),
                ProductFragment.newInstance(),
                InfoFragment.newInstance(),
                MineFragment.newInstance())
        return AdapterViewPager((view.getWrapContext() as AppCompatActivity).supportFragmentManager, fragments)
    }

}

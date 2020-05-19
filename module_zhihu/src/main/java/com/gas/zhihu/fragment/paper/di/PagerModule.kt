package com.gas.zhihu.fragment.paper.di

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.paper.PaperAdapter
import com.gas.zhihu.fragment.paper.mvp.PagerContract
import com.gas.zhihu.fragment.paper.mvp.PagerModel
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */
@Module
//构建PagerModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PagerModule(private val view: PagerContract.View) {


    @FragmentScope
    @Provides
    fun providePagerModel(model: PagerModel): PagerContract.Model {
        return model
    }


    @FragmentScope
    @Provides
    fun providePagerView(): PagerContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this.view.getWrapActivity())
    }

    @FragmentScope
    @Provides
    fun providePagerAdapter(): PaperAdapter {
        val adapter = PaperAdapter(this.view.getWrapActivity())
        return adapter
    }

}

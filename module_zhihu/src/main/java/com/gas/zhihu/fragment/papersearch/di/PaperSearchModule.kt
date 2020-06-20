package com.gas.zhihu.fragment.papersearch.di

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.lib.di.scope.FragmentScope
import com.gas.zhihu.fragment.paper.PaperAdapter
import com.gas.zhihu.fragment.paper.mvp.PagerContract

import dagger.Module
import dagger.Provides
import com.gas.zhihu.fragment.papersearch.mvp.PaperSearchContract
import com.gas.zhihu.fragment.papersearch.mvp.PaperSearchModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 06/20/2020 17:46
 * ================================================
 */
@Module
//构建PaperSearchModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PaperSearchModule(private val view: PaperSearchContract.View) {


    @FragmentScope
    @Provides
    fun providePaperSearchModel(model: PaperSearchModel): PaperSearchContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun providePaperSearchView(): PaperSearchContract.View {
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
        val adapter = PaperAdapter(this.view.getWrapActivity(),false,false)
        return adapter
    }

}

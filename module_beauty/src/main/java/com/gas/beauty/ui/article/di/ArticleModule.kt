/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gas.beauty.ui.article.di

import androidx.fragment.app.Fragment
import com.base.lib.di.scope.FragmentScope
import com.gas.beauty.R
import com.gas.beauty.ui.article.mvp.ArticleContract
import com.gas.beauty.ui.article.mvp.ArticleModel
import com.gas.beauty.ui.classify.ClassifyFragment
import com.lib.commonsdk.adapter.AdapterViewPager
import dagger.Module
import dagger.Provides
import java.util.*

/**
 * ================================================
 * 展示 Module 的用法
 *
 * @see [Module wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.2.4.5)
 * Created by JessYan on 09/04/2016 11:10
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
@Module
class ArticleModule(private val view: ArticleContract.View) {

    @FragmentScope
    @Provides
    fun provideTitles(): Array<String> {
        return view.wrapContext.resources.getStringArray(R.array.gank_title)
    }

    @FragmentScope
    @Provides
    fun provideFragments(titles: Array<String>):@JvmSuppressWildcards List<Fragment> {
        val fragments: MutableList<Fragment> = ArrayList()
        for (subtype in titles) {
            fragments.add(ClassifyFragment.newInstance(subtype))
        }
        return fragments
    }

    @FragmentScope
    @Provides
    fun providePagerAdapter(view: ArticleContract.View, fragments:@JvmSuppressWildcards List<Fragment>, titles: Array<String>): AdapterViewPager {
        return AdapterViewPager(view.currentFragment.childFragmentManager, fragments, titles)
    }

    @FragmentScope
    @Provides
    fun provideArticleView(): ArticleContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideArticleModel(model: ArticleModel): ArticleContract.Model {
        return model
    }


}
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
package com.gas.beauty.article.di;

import androidx.fragment.app.Fragment;

import com.base.lib.di.scope.FragmentScope;
import com.gas.beauty.R;
import com.gas.beauty.article.mvp.ArticleContract;
import com.gas.beauty.ui.article.classify.ClassifyFragment;
import com.lib.commonsdk.adapter.AdapterViewPager;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 展示 Module 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.5">Module wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Module
public abstract class ArticleModule {


    @FragmentScope
    @Provides
    static String[] provideTitles(ArticleContract.View view) {
        return view.getWrapContext().getResources().getStringArray(R.array.gank_title);
    }


    @FragmentScope
    @Provides
    static AdapterViewPager providePagerAdapter(ArticleContract.View view, List<Fragment> fragments,String[] titles) {
        return new AdapterViewPager(view.getCurrentFragment().getChildFragmentManager(), fragments, titles);
    }

    @FragmentScope
    @Provides
    static List<Fragment> provideFragments( String[] titles ) {
        List<Fragment> fragments = new ArrayList<>();
        for (String subtype : titles) {
            fragments.add(ClassifyFragment.newInstance(subtype));
        }
        return fragments;
    }


}

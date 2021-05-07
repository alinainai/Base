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
package com.gas.beauty.ui.classify.di

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.lib.di.scope.FragmentScope
import com.gas.beauty.ui.classify.ClassifyAdapter
import com.gas.beauty.ui.classify.mvp.ClassifyContract
import com.gas.beauty.ui.classify.mvp.ClassifyModle
import dagger.Binds
import dagger.Module
import dagger.Provides

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
abstract class ClassifyModule {
    @Binds
    abstract fun bindClassifModel(model: ClassifyModle): ClassifyContract.Model

    companion object {
        @JvmStatic
        @FragmentScope
        @Provides
        fun provideLayoutManager(view: ClassifyContract.View): RecyclerView.LayoutManager {
            return GridLayoutManager(view.wrapContext, 2)
        }

        @JvmStatic
        @FragmentScope
        @Provides
        fun provideClasseAdapter(view: ClassifyContract.View): ClassifyAdapter {
            return ClassifyAdapter(view.wrapContext)
        }
    }
}
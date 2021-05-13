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
package com.gas.beauty.ui.main.di

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.lib.di.scope.ActivityScope
import com.gas.beauty.ui.main.MainAdapter
import com.gas.beauty.ui.main.mvp.MainContract
import com.gas.beauty.ui.main.mvp.MainModel
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
abstract class MainModule {
    @Binds
    abstract fun bindGankModel(model: MainModel): MainContract.Model

    companion object {
        @JvmStatic
        @ActivityScope
        @Provides
        fun provideLayoutManager(view: MainContract.View): RecyclerView.LayoutManager {
            return GridLayoutManager(view.activity, 2)
        }

        @JvmStatic
        @ActivityScope
        @Provides
        fun provideMainAdapter(view: MainContract.View): MainAdapter {
            return MainAdapter(view.activity)
        }
    }
}
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
package com.gas.beauty.ui.home.di

import com.base.lib.di.component.AppComponent
import com.base.lib.di.scope.ActivityScope
import com.gas.beauty.ui.home.HomeActivity
import com.gas.beauty.ui.home.mvp.HomeContract
import dagger.BindsInstance
import dagger.Component

/**
 * ================================================
 * 展示 Component 的用法
 *
 * @see [Component wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.2.4.6)
 * Created by JessYan on 09/04/2016 11:17
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
@ActivityScope
@Component(dependencies = [AppComponent::class])
interface HomeComponent {
    fun inject(activity: HomeActivity?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: HomeContract.View?): Builder?
        fun appComponent(appComponent: AppComponent?): Builder?
        fun build(): HomeComponent?
    }
}
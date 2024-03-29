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
package com.gas.zhihu.ui.fragment.mine.di

import com.gas.zhihu.ui.fragment.mine.mvp.MineContract
import com.gas.zhihu.ui.fragment.mine.mvp.MineModle
import dagger.Binds
import dagger.Module

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
abstract class MineModule {
    @Binds
    abstract fun bindClassifModel(model: MineModle?): MineContract.Model?
}
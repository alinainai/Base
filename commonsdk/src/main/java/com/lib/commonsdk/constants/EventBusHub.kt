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
package com.lib.commonsdk.constants

/**
 * ================================================
 * AndroidEventBus 作为本方案提供的另一种跨组件通信方式 (第一种是接口下沉, 在 app 的 MainActivity 中已展示, 通过 ARouter 实现)
 * AndroidEventBus 比 greenrobot 的 EventBus 多了一个 Tag, 在组件化中更容定位和管理事件
 *
 *
 * EventBusHub 用来定义 AndroidEventBus 的 Tag 字符串, 以组件名作为 Tag 前缀, 对每个组件的事件进行分组
 * Tag 的命名规则为 组件名 + 页面名 + 动作
 * 比如需要使用 AndroidEventBus 通知订单组件的订单详情页进行刷新, 可以将这个刷新方法的 Tag 命名为 "order/OrderDetailActivity/refresh"
 *
 *
 * Tips: 基础库中的 EventBusHub 仅用来存放需要跨组件通信的事件的 Tag, 如果某个事件只想在组件内使用 AndroidEventBus 进行通信
 * 那就让组件自行管理这个事件的 Tag
 *
 * @see [EventBusHub wiki 官方文档](https://github.com/JessYanCoding/ArmsComponent/wiki.3.5)
 * Created by JessYan on 30/03/2018 17:46
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface EventBusHub {
    companion object {
        /**
         * 组名
         */
        const val APP = "app" //宿主 App 组件
        const val ZHIHU = "zhihu" //知乎组件
        const val GANK = "gank" //知乎组件
        const val TEST = "test" //知乎组件
    }
}
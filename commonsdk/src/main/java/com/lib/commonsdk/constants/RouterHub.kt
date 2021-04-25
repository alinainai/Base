/*
 * Copyright 2018 JessYan
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
 * RouterHub 用来定义路由器的路由地址, 以组件名作为前缀, 对每个组件的路由地址进行分组, 可以统一查看和管理所有分组的路由地址
 *
 *
 * RouterHub 存在于基础库, 可以被看作是所有组件都需要遵守的通讯协议, 里面不仅可以放路由地址常量, 还可以放跨组件传递数据时命名的各种 Key 值
 * 再配以适当注释, 任何组件开发人员不需要事先沟通只要依赖了这个协议, 就知道了各自该怎样协同工作, 既提高了效率又降低了出错风险, 约定的东西自然要比口头上说强
 *
 *
 * 如果您觉得把每个路由地址都写在基础库的 RouterHub 中, 太麻烦了, 也可以在每个组件内部建立一个私有 RouterHub, 将不需要跨组件的
 * 路由地址放入私有 RouterHub 中管理, 只将需要跨组件的路由地址放入基础库的公有 RouterHub 中管理, 如果您不需要集中管理所有路由地址的话
 * 这也是比较推荐的一种方式
 *
 *
 * 路由地址的命名规则为 组件名 + 页面名, 如订单组件的订单详情页的路由地址可以命名为 "/order/OrderDetailActivity"
 *
 *
 * ARouter 将路由地址中第一个 '/' 后面的字符称为 Group, 比如上面的示例路由地址中 order 就是 Group, 以 order 开头的地址都被分配该 Group 下
 * 切记不同的组件中不能出现名称一样的 Group, 否则会发生该 Group 下的部分路由地址找不到的情况!!!
 * 所以每个组件使用自己的组件名作为 Group 是比较好的选择, 毕竟组件不会重名
 *
 * @see [RouterHub wiki 官方文档](https://github.com/JessYanCoding/ArmsComponent/wiki.3.4)
 * Created by JessYan on 30/03/2018 18:07
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface RouterHub {
    companion object {
        /**
         * 组名
         */
        const val APP = "/app" //宿主 App 组件
        const val ZHIHU = "/zhihu" //知乎组件
        const val GANK = "/gank" //知乎组件
        const val TEST = "/test" //知乎组件

        /**
         * 服务组件, 用于给每个组件暴露特有的服务
         */
        const val SERVICE = "/service"

        /**
         * 宿主 App 分组
         */
        const val APP_SPLASHACTIVITY = APP + "/SplashActivity"
        const val APP_MAINACTIVITY = APP + "/MainActivity"
        const val APP_WEBVIEWACTIVITY = APP + "/WebViewActivity"

        /**
         * 知乎分组
         */
        const val ZHIHU_SERVICE_ZHIHUINFOSERVICE = ZHIHU + SERVICE + "/ZhihuInfoService"
        const val ZHIHU_HOMEACTIVITY = ZHIHU + "/MainActivity"
        const val ZHIHU_LOGINACTIVITY = ZHIHU + "/LoginActivity"
        const val ZHIHU_DETAILACTIVITY = ZHIHU + "/DetailActivity"

        /**
         * 干货分组
         */
        const val GANK_SERVICE_GANKINFOSERVICE = GANK + SERVICE + "/GankInfoService"
        const val GANK_MAINACTIVITY = GANK + "/MainActivity"
        const val GANK_HOMEACTIVITY = GANK + "/HomeActivity"

        /**
         * 测试分组
         */
        const val TEST_SERVICE_TESTINFOSERVICE = TEST + SERVICE + "/TestInfoService"
        const val TEST_HOMEACTIVITY = TEST + "/HomeActivity"
    }
}
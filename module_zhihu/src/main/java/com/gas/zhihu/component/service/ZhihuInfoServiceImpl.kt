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
package com.gas.zhihu.component.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.componentservice.zhihu.bean.ZhihuInfo
import com.base.componentservice.zhihu.service.ZhihuInfoService
import com.lib.commonsdk.constants.RouterHub

/**
 * ================================================
 * 向外提供服务的接口实现类, 在此类中实现一些具有特定功能的方法提供给外部, 即可让一个组件与其他组件或宿主进行交互
 *
 * @see [CommonService wiki 官方文档](https://github.com/JessYanCoding/ArmsComponent/wiki.2.2.3.2)
 * Created by JessYan on 2018/4/27 14:27
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
@Route(path = RouterHub.ZHIHU_SERVICE_ZHIHUINFOSERVICE, name = "知乎信息服务")
class ZhihuInfoServiceImpl : ZhihuInfoService {
    private var mContext: Context? = null
    override fun getInfo(): ZhihuInfo {
        return ZhihuInfo("知乎日报")
    }

    override fun init(context: Context) {
        mContext = context
    }
}
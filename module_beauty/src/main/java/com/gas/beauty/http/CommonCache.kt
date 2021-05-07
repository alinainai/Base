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
package com.gas.beauty.http

import com.gas.beauty.bean.GankItemBean
import io.reactivex.Observable
import io.rx_cache2.*
import java.util.concurrent.TimeUnit

/**
 * ================================================
 * 展示 [RxCache.using] 中需要传入的 Providers 的使用方式
 *
 *
 * Created by JessYan on 08/30/2016 13:53
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
@EncryptKey("big")
interface CommonCache {
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    @Encrypt
    fun getGankItemData(items: Observable<List<GankItemBean>>, key: DynamicKey, evictProvider: EvictProvider): Observable<Reply<List<GankItemBean>>>
}
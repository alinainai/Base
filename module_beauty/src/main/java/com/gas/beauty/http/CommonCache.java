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
package com.gas.beauty.http;

import com.gas.beauty.bean.GankItemBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.Encrypt;
import io.rx_cache2.EncryptKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;

/**
 * ================================================
 * 展示 {@link RxCache#using(Class)} 中需要传入的 Providers 的使用方式
 * <p>
 * Created by JessYan on 08/30/2016 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@EncryptKey("big")
public interface CommonCache {
    @LifeCache(duration = 2, timeUnit = TimeUnit.DAYS)
    @Encrypt()
    Observable<Reply<List<GankItemBean>>> getGankItemData(Observable<List<GankItemBean>> items, DynamicKey key, EvictProvider evictProvider);
}

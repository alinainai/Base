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
package com.base.lib.base.delegate.activity;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.baseui.view.StatusLine;
import com.base.baseui.view.TitleView;
import com.base.lib.base.BaseActivity;
import com.base.lib.cache.Cache;
import com.base.lib.di.component.AppComponent;


/**
 * ================================================
 * 框架要求框架中的每个 {@link Activity} 都需要实现此类,以满足规范
 *
 * @see BaseActivity
 * Created by JessYan on 26/04/2017 21:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface IActivity {

    @NonNull
    Cache<String, Object> provideCache();

    void setupActivityComponent(@NonNull AppComponent appComponent);

    int initLayoutId();

    void initView(@Nullable Bundle savedInstanceState);

    void setListener();

    void initData();

    /**
     * 是否使用Fragment
     *
     * @return 默认false
     */
    boolean useFragment();

    /**
     * 是否需要TitleBar
     *
     * @return 默认true
     */
    boolean needTitle();

    /**
     * 获取
     *
     * @param titleView TitleView
     */
    void getTitleView(TitleView titleView);

    /**
     * 是否需要StatusBar
     *
     * @return 默认true
     */
    boolean needStatusBar();

    /**
     * @param statusBar StatusBarHeight
     */
    void getStatusBarHeight(StatusLine statusBar);
}

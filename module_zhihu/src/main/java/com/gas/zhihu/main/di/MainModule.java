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
package com.gas.zhihu.main.di;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.lib.di.scope.ActivityScope;
import com.gas.zhihu.app.ZhihuConstants;
import com.gas.zhihu.main.MainAdapter;
import com.gas.zhihu.main.mvp.MainContract;
import com.gas.zhihu.main.mvp.MainModel;
import com.lib.commonsdk.constants.Constants;
import com.lib.commonsdk.constants.RouterHub;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * 展示 Module 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.5">Module wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindZhihuModel(MainModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MainContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }

    @ActivityScope
    @Provides
    static NavigationCallback provideNavigationCallback(MainContract.View view) {

        return new NavigationCallback() {

            @Override
            public void onFound(Postcard postcard) {

            }

            @Override
            public void onLost(Postcard postcard) {
                if (RouterHub.APP_WEBVIEWACTIVITY.equals(postcard.getPath())) {
                    ARouter.getInstance()
                            .build(RouterHub.ZHIHU_DETAILACTIVITY)
                            .with(postcard.getExtras())
                            .greenChannel()
                            .navigation(view.getActivity());
                }
            }

            @Override
            public void onArrival(Postcard postcard) {

            }

            @Override
            public void onInterrupt(Postcard postcard) {

            }
        };
    }

    @ActivityScope
    @Provides
    static RecyclerView.Adapter provideMainAdapter(MainContract.View view, NavigationCallback callback) {
        MainAdapter adapter = new MainAdapter(view.getActivity());
        adapter.setOnMultiItemClickListener((viewHolder, data, position, viewType) -> ARouter.getInstance()
                .build(RouterHub.APP_WEBVIEWACTIVITY)
                .withInt(ZhihuConstants.DETAIL_ID, data.getId())
                .withString(Constants.PUBLIC_TITLE, data.getTitle())
                .withString(Constants.PUBLIC_URL, data.getUrl())
                .navigation(view.getActivity(), callback));

        return adapter;
    }


}

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

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.lib.di.scope.ActivityScope;
import com.base.paginate.PageViewHolder;
import com.base.paginate.interfaces.OnMultiItemClickListeners;
import com.gas.zhihu.app.ZhihuConstants;
import com.gas.zhihu.bean.DailyListBean;
import com.gas.zhihu.main.MainAdapter;
import com.gas.zhihu.main.mvp.MainContract;
import com.gas.zhihu.main.mvp.MainModel;
import com.lib.commonsdk.consants.RouterHub;

import java.util.ArrayList;
import java.util.List;

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
    static List<DailyListBean.StoriesBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static RecyclerView.Adapter provideMainAdapter(MainContract.View view){
        MainAdapter adapter = new MainAdapter(view.getActivity());
        adapter.setOnMultiItemClickListener(new OnMultiItemClickListeners<DailyListBean.StoriesBean>() {
            @Override
            public void onItemClick(PageViewHolder viewHolder, DailyListBean.StoriesBean data, int position, int viewType) {
                ARouter.getInstance()
                        .build(RouterHub.ZHIHU_DETAILACTIVITY)
                        .withInt(ZhihuConstants.DETAIL_ID, data.getId())
                        .withString(ZhihuConstants.DETAIL_TITLE, data.getTitle())
                        .withString(ZhihuConstants.DETAIL_URL,data.getTitle())
                        .navigation(view.getActivity());
            }
        });

        return adapter;
    }


}

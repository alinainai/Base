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
package trunk.doi.base.ui.fragment.classify.di;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.lib.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import trunk.doi.base.ui.fragment.classify.ClassifyAdapter;
import trunk.doi.base.ui.fragment.classify.mvp.ClassifyContract;
import trunk.doi.base.ui.fragment.classify.mvp.ClassifyModle;

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
public abstract class ClassifyModule {
    @Binds
    abstract ClassifyContract.Model bindClassifModel(ClassifyModle model);


    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ClassifyContract.View view) {
        return new GridLayoutManager(view.getWrapContext(), 2);
    }

    @FragmentScope
    @Provides
    static ClassifyAdapter provideClasseAdapter(ClassifyContract.View view){
        return new ClassifyAdapter(view.getWrapContext(),new ArrayList<>());
    }




}

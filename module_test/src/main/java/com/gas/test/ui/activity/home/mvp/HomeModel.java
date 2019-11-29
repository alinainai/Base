package com.gas.test.ui.activity.home.mvp;


import com.base.componentservice.test.bean.TestInfo;
import com.base.lib.integration.repository.IRepositoryManager;
import com.base.lib.mvp.BaseModel;

import com.base.lib.di.scope.ActivityScope;

import javax.inject.Inject;

import com.gas.test.bean.TestInfoBean;
import com.gas.test.ui.activity.home.mvp.HomeContract;
import com.gas.test.ui.activity.show.IShowConst;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */

@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public List<TestInfoBean> getInfoItems() {
        List<TestInfoBean> beans= new ArrayList<>();
        beans.add(new TestInfoBean("指定宽高比的自定义View", IShowConst.RATIOVIEWFRAGMENT));
        beans.add(new TestInfoBean("检测自定义RecyclerView的适配器", IShowConst.ADAPTERFRAGMENT));
        return beans;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
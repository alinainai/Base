package com.gas.zhihu.ui.main;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.zhihu.R;
import com.gas.zhihu.R2;
import com.gas.zhihu.fragment.option.OptionFragment;
import com.gas.zhihu.ui.main.di.DaggerMainComponent;
import com.gas.zhihu.ui.main.mvp.MainContract;
import com.gas.zhihu.ui.main.mvp.MainPresenter;
import com.lib.commonsdk.constants.RouterHub;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = RouterHub.ZHIHU_LOGINACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R2.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R2.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R2.id.fragment_container)
    SwipeRefreshLayout fragment_container;



    private  boolean isExit;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Inject
    RxPermissions mRxPermissions;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.zhihu_activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mSwipeRefreshLayout.setColorSchemeResources(R.color.public_white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.public_black));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

//        mSwipeRefreshLayout.setRefreshing(true);
//        mPresenter.requestDailyList();

       FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, OptionFragment.Companion.newInstance()).commit();

    }



    @Override
    public void onRefresh() {
        mPresenter.requestDailyList();
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void success() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }


    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onBackPressed() {
        exitBy2Click(); //调用双击退出函数
    }

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            ArmsUtils.snackbarText("再按一次退出程序");
            tExit =new Timer();
            tExit.schedule(new TimerTask() {


                @Override
                public void run() {
                    isExit = false;
                }

            }, 2000); //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            ArmsUtils.exitApp();
        }
    }
}

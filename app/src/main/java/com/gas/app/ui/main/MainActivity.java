package com.gas.app.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.baseui.view.NoScrollViewPager;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.lib.commonsdk.adapter.AdapterViewPager;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.statusbar.StatusBarManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import com.gas.app.R;
import com.gas.app.ui.main.di.DaggerMainComponent;
import com.gas.app.ui.main.mvp.MainContract;
import com.gas.app.ui.main.mvp.MainPresenter;
import com.gas.app.utils.AppMoudleUtil;


/**
 * 框架搭建
 */
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    @BindView(R.id.container)
    NoScrollViewPager mVp;

    @BindView(R.id.main_btn1)
    TextView rBtn1;
    @BindView(R.id.mainBtn2)
    TextView rBtn2;
    @BindView(R.id.mainBtn3)
    TextView rBtn3;
    @BindView(R.id.mainBtn4)
    TextView rBtn4;

    @BindView(R.id.main_icon1)
    LottieAnimationView mIcon1;
    @BindView(R.id.mainIcon2)
    LottieAnimationView mIcon2;
    @BindView(R.id.mainIcon3)
    LottieAnimationView mIcon3;
    @BindView(R.id.mainIcon4)
    LottieAnimationView mIcon4;

    @Inject
    AdapterViewPager mAdapter;
    @Inject
    List<Fragment> mFragments;


    private static final String CURRENTTABINDEX = "current_tab_index";
    // 当前fragment的index
    public int mCurrentTabIndex = -1;
    public int mSavedTabIndex = -1;


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
        StatusBarManager.fullTransStatusBar(this);
        StatusBarManager.setStatusBarLightMode(this);
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mVp.setAdapter(mAdapter);
        mVp.setOffscreenPageLimit(mFragments.size() - 1);

        //防止屏幕切换等操作造成按钮错位
        if (null != savedInstanceState) {
            mSavedTabIndex = savedInstanceState.getInt(CURRENTTABINDEX, -1);
            if (mSavedTabIndex != -1)
                resetButton(mSavedTabIndex);
            else
                resetButton(0);
        } else {
            resetButton(0);
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //防止屏幕切换等操作造成按钮错位
        outState.putInt(CURRENTTABINDEX, mCurrentTabIndex);
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.main_btn1, R.id.mainBtn2, R.id.mainBtn3, R.id.mainBtn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_btn1:
                resetButton(0);
                break;
            case R.id.mainBtn2:
                resetButton(1);
                break;
            case R.id.mainBtn3:
                resetButton(2);
                break;
            case R.id.mainBtn4:
                resetButton(3);
                break;
        }
    }

    public void resetButton(int index) {

        if (index == mCurrentTabIndex)
            return;

        switch (index) {
            case 0:
                rBtn1.setSelected(true);
                rBtn2.setSelected(false);
                rBtn3.setSelected(false);
                rBtn4.setSelected(false);
                AppMoudleUtil.startLottieAnimation(mIcon1);
                AppMoudleUtil.stopLottieAnimation(mIcon2);
                AppMoudleUtil.stopLottieAnimation(mIcon3);
                AppMoudleUtil.stopLottieAnimation(mIcon4);
                break;
            case 1:
                rBtn1.setSelected(false);
                rBtn2.setSelected(true);
                rBtn3.setSelected(false);
                rBtn4.setSelected(false);
                AppMoudleUtil.stopLottieAnimation(mIcon1);
                AppMoudleUtil.startLottieAnimation(mIcon2);
                AppMoudleUtil.stopLottieAnimation(mIcon3);
                AppMoudleUtil.stopLottieAnimation(mIcon4);
                break;
            case 2:
                rBtn1.setSelected(false);
                rBtn2.setSelected(false);
                rBtn3.setSelected(true);
                rBtn4.setSelected(false);
                AppMoudleUtil.stopLottieAnimation(mIcon1);
                AppMoudleUtil.stopLottieAnimation(mIcon2);
                AppMoudleUtil.startLottieAnimation(mIcon3);
                AppMoudleUtil.stopLottieAnimation(mIcon4);
                break;
            case 3:
                rBtn1.setSelected(false);
                rBtn2.setSelected(false);
                rBtn3.setSelected(false);
                rBtn4.setSelected(true);
                AppMoudleUtil.stopLottieAnimation(mIcon1);
                AppMoudleUtil.stopLottieAnimation(mIcon2);
                AppMoudleUtil.stopLottieAnimation(mIcon3);
                AppMoudleUtil.startLottieAnimation(mIcon4);
                break;
        }

        mVp.setCurrentItem(index, false);
        mCurrentTabIndex = index;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            ArmsUtils.snackbarText("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ArmsUtils.exitApp();
        }
    }


    @Override
    public Context getWrapContext() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


}

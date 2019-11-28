package trunk.doi.base.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.lib.commonsdk.adapter.AdapterViewPager;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.utils.statusbar.StatusBarManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.ui.main.di.DaggerMainComponent;
import trunk.doi.base.ui.main.mvp.MainContract;
import trunk.doi.base.ui.main.mvp.MainPresenter;


/**
 * 框架搭建
 */
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.container)
    ViewPager mVp;

    @BindView(R.id.home_btn)
    Button rBtn1;
    @BindView(R.id.classify_btn)
    Button rBtn2;
    @BindView(R.id.account_btn)
    Button rBtn3;
    @BindView(R.id.shopping_btn)
    Button rBtn4;

    @Inject
    AdapterViewPager mAdapter;
    @Inject
    List<Fragment> mFragments;

    // 当前fragment的index
    public int mCurrentTabIndex = 0;
    private static final String CURRENTTABINDEX = "current_tab_index";

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
        //防止屏幕切换等操作造成按钮错位
        if (null != savedInstanceState) {
            mCurrentTabIndex = savedInstanceState.getInt(CURRENTTABINDEX, 0);
        }

        mVp.setAdapter(mAdapter);
        mVp.setOffscreenPageLimit(mFragments.size() - 1);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        resetButton(mCurrentTabIndex);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //防止屏幕切换等操作造成按钮错位
        outState.putInt(CURRENTTABINDEX, mCurrentTabIndex);
        super.onSaveInstanceState(outState);
    }

    @OnClick({R.id.home_btn, R.id.classify_btn, R.id.account_btn, R.id.shopping_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_btn:
                resetButton(0);
                break;
            case R.id.classify_btn:
                resetButton(1);
                break;
            case R.id.account_btn:
                resetButton(2);
                break;
            case R.id.shopping_btn:
                resetButton(3);
                break;
        }
    }

    public void resetButton(int index) {

        switch (index) {
            case 0:
                rBtn1.setSelected(true);
                rBtn2.setSelected(false);
                rBtn3.setSelected(false);
                rBtn4.setSelected(false);
                break;
            case 1:
                rBtn1.setSelected(false);
                rBtn2.setSelected(true);
                rBtn3.setSelected(false);
                rBtn4.setSelected(false);
                break;
            case 2:
                rBtn1.setSelected(false);
                rBtn2.setSelected(false);
                rBtn3.setSelected(true);
                rBtn4.setSelected(false);
                break;
            case 3:
                rBtn1.setSelected(false);
                rBtn2.setSelected(false);
                rBtn3.setSelected(false);
                rBtn4.setSelected(true);
                break;
        }
        if (index == mCurrentTabIndex)
            return;
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

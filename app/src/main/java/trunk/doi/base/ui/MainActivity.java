package trunk.doi.base.ui;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.lib.commonsdk.core.RouterHub;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.ui.fragment.AdapterFragment;
import trunk.doi.base.ui.fragment.main.MainFragment;
import trunk.doi.base.ui.fragment.NewsFragment;
import trunk.doi.base.ui.fragment.mine.MineFragment;
import trunk.doi.base.util.ActivityController;
import trunk.doi.base.util.ToastUtil;


/**
 * Created by ly on 2016/5/27 13:36.
 * 框架搭建
 */
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx

@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity {


    @BindView(R.id.home_btn)
    Button rBtn1;
    @BindView(R.id.classify_btn)
    Button rBtn2;
    @BindView(R.id.account_btn)
    Button rBtn3;
    @BindView(R.id.shopping_btn)
    Button rBtn4;
    //container
    private FragmentManager mFragManager;//fragment管理器
    private BaseFragment mHomeFragment;//首页的fragment
    private BaseFragment mAdapterFragment;
    private BaseFragment mNewsFragment;
    private BaseFragment mMineFragment;

    // 当前fragment的index
    public int mCurrentTabIndex = 0;
    private static final String CURRENTTABINDEX = "CURRENTTABINDEX";


    private static final SparseArray<String> FRAGMENTS = new SparseArray<String>() {
        {
            put(0, AdapterFragment.TAG);
            put(1, MainFragment.TAG);
            put(2, NewsFragment.TAG);
            put(3, MineFragment.TAG);
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {


    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_main;
    }

    //如果不需要tutleBar这个一定要返回false
    @Override
    public boolean needTitle() {
        return false;
    }
    //如果不需要StatusBar这个一定要返回false
    @Override
    public boolean needStatusBar() {
        return false;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mFragManager = getSupportFragmentManager();
        //防止屏幕切换等操作造成按钮错位
        if (null != savedInstanceState) {
            mCurrentTabIndex = savedInstanceState.getInt(CURRENTTABINDEX, 0);
        }
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
                if (mCurrentTabIndex == 0) {
                    return;
                }
                resetButton(0);
                break;
            case R.id.classify_btn:
                if (mCurrentTabIndex == 1) {
                    return;
                }
                resetButton(1);
                break;
            case R.id.account_btn:
                if (mCurrentTabIndex == 2) {
                    return;
                }
                resetButton(2);
                break;
            case R.id.shopping_btn:
                if (mCurrentTabIndex == 3) {
                    return;
                }
                resetButton(3);
                break;
        }
    }

    public void changeFragment(int from, int to) {
        FragmentTransaction transaction = mFragManager.beginTransaction();
        //Fragment切换时的动画
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        Fragment tofragment = mFragManager.findFragmentByTag(FRAGMENTS.get(to));

        if (tofragment == null) {
            tofragment = getFragmentFromFactory(FRAGMENTS.get(to));
        }
        Fragment fromfragment = mFragManager.findFragmentByTag(FRAGMENTS.get(from));
        if (fromfragment != null) {
            transaction.hide(fromfragment);
        }
        if (!tofragment.isAdded()) {
            transaction.add(R.id.container, tofragment, FRAGMENTS.get(to));
        }
        transaction.show(tofragment).commitNow();
    }

    @NonNull
    private Fragment getFragmentFromFactory(String tag) {

        Fragment fragment;
        switch (tag) {
            case MainFragment.TAG:
                if (mHomeFragment == null) {
                    mHomeFragment = MainFragment.newInstance();
                }
                fragment = mHomeFragment;
                break;
            case NewsFragment.TAG:
                if (mNewsFragment == null) {
                    mNewsFragment = NewsFragment.newInstance();
                }
                fragment = mNewsFragment;
                break;
            case AdapterFragment.TAG:
                if (mAdapterFragment == null) {
                    mAdapterFragment = AdapterFragment.newInstance();
                }
                fragment = mAdapterFragment;
                break;
            case MineFragment.TAG:
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance();
                }
                fragment = mMineFragment;
                break;
            default:
                throw new NullPointerException("the tag of Fragment is invalid!!!");
        }
        return fragment;
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

        changeFragment(mCurrentTabIndex, index);
        // 把当前tab设为选中状态
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
            ToastUtil.show(mContext, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ActivityController.getActivityController().closeAllActivity(mContext);
            finish();
        }
    }

}

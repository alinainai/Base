package trunk.doi.base.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.base.baseui.view.StatusBarHeight;
import com.base.baseui.view.TitleView;
import com.base.lib.base.BaseActivity;
import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.lib.rx.RxBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.R;
import trunk.doi.base.bean.rxmsg.MainEvent;
import trunk.doi.base.ui.fragment.MainFragment;
import trunk.doi.base.ui.fragment.NewsFragment;
import trunk.doi.base.util.ActivityController;
import trunk.doi.base.util.ToastUtil;


/**
 * Created by ly on 2016/5/27 13:36.
 * 框架搭建
 */
@SuppressLint("UseSparseArrays")
public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_radio)
    RadioGroup rg_radio;
    @BindView(R.id.classify_btn)
    RadioButton classify_btn;
    //container
    private FragmentManager mFragManager;//fragment管理器
    private BaseFragment mHomeFragment;//首页的fragment
    private BaseFragment mClassifyFragment;//分类的fragment
    private BaseFragment mInfoFragment;//购物车的fragment
    private BaseFragment mMineFragment;//我的fragment

    private int index;
    // 当前fragment的index
    public int currentTabIndex;
    private Disposable disposable;


    private static final Map<Integer, String> FRAGMENTS = new HashMap<Integer, String>() {
        {
            put(0, NewsFragment.TAG);
            put(1, MainFragment.TAG);
            put(2, MainFragment.TAG);
            put(3, MainFragment.TAG);
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void getStatusBarHeight(StatusBarHeight statusBar) {
        super.getStatusBarHeight(statusBar);
        statusBar.setBackgroundResource(R.color.cff3e19);
    }

    @Override
    public void getTitleView(TitleView titleView) {
        super.getTitleView(titleView);

    }

    @Override
    public boolean needTitle() {
        return false;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFragManager = getSupportFragmentManager();
    }

    @Override
    public void setListener() {

        rg_radio.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.home_btn:
                    index = 0;
                    break;
                case R.id.classify_btn:
                    index = 1;
                    break;
                case R.id.account_btn:
                    index = 2;
                    break;
                case R.id.shopping_btn:
                    index = 3;
                    break;
            }
            changeFragment(currentTabIndex, index);
            // 把当前tab设为选中状态
            currentTabIndex = index;
        });

        disposable = RxBus.getDefault().toObservable(MainEvent.class)
                .subscribe(event -> {

                    switch (event.getId()) {
                        case 0:
                            changeFragment(currentTabIndex, 0);
                            break;
                        case 1:
                            changeFragment(currentTabIndex, 1);
                            break;
                        case 2:
                            changeFragment(currentTabIndex, 2);
                            break;
                        case 3:
                            changeFragment(currentTabIndex, 3);
                            break;
                    }
                    ((RadioButton) rg_radio.getChildAt(event.getId())).setChecked(true);
                });


    }


    @Override
    public void initData() {
        resetButton();
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
        transaction.show(tofragment).commitAllowingStateLoss();
    }

    @Nullable
    private Fragment getFragmentFromFactory(String tag) {
        if (MainFragment.TAG.equals(tag)) {
            if (mHomeFragment == null) {
                mHomeFragment = MainFragment.newInstance();
            }
            return mHomeFragment;
        }
        if (NewsFragment.TAG.equals(tag)) {
            if (mClassifyFragment == null) {
                mClassifyFragment = NewsFragment.newInstance();
            }
            return mClassifyFragment;
        }

        return null;
    }

    public void resetButton() {
        index = 0;
        changeFragment(currentTabIndex, index);
        // 把第一个tab设为选中状态
        currentTabIndex = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
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

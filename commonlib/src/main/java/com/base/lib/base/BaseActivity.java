package com.base.lib.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;

import com.base.lib.R;
import com.base.lib.base.delegate.activity.IActivity;
import com.base.lib.cache.Cache;
import com.base.lib.cache.CacheType;
import com.base.lib.lifecycle.ActivityIRxLifecycle;
import com.base.lib.mvp.IPresenter;
import com.base.lib.util.ArmsUtils;
import com.base.lib.view.StatusBarHeight;
import com.base.lib.view.TitleView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;


/**
 * Activity的基类
 * 依赖activity_base布局
 * activity_base 初始化 @TitleView title和status_bar
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, ActivityIRxLifecycle {

    protected final String TAG = this.getClass().getSimpleName();
    protected AppCompatActivity mContext;
    protected Unbinder mBinder;
    private Cache<String, Object> mCache;

    /**
     * 如果当前页面逻辑简单, Presenter 可以为 null
     */
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.getAppComponent(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    //RxLifecycle
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return lifecycleSubject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //允许使用 Ver_5.0 转换动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        // 不需要toolbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);


        //状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏

            View decorView = getWindow().getDecorView();
            //拓展布局到状态栏后面 | 稳定的布局，不会随系统栏的隐藏、显示而变化
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.trans));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏

            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);

        }

        mContext = this;
        setContentView(R.layout.activity_base);

        //初始化界面内容layout
        StatusBarHeight statusBar = findViewById(R.id.v_status_bar);
        TitleView titleView = findViewById(R.id.v_title);
        ViewStub viewContent = findViewById(R.id.base_fragment_content);
        if (needTitle()) {
            if (titleView.getVisibility() != View.VISIBLE)
                titleView.setVisibility(View.VISIBLE);
        } else {
            if (titleView.getVisibility() != View.GONE)
                titleView.setVisibility(View.GONE);
        }
        if (needStatusBar()) {
            if (statusBar.getVisibility() != View.VISIBLE)
                statusBar.setVisibility(View.VISIBLE);
        } else {
            if (statusBar.getVisibility() != View.GONE)
                statusBar.setVisibility(View.GONE);
        }
        getTitleView(titleView);
        getStatusBarHeight(statusBar);
        viewContent.setLayoutResource(initLayoutId());

        //添加ButterKnife绑定
        mBinder = ButterKnife.bind(this, viewContent.inflate());

        initView(savedInstanceState);
        setListener();
        initData();

    }

    /**
     * 设置监听器
     * 默认实现，需要的时候子类重写
     * 比喻添加EditText的focus获取事件
     */
    public void setListener() {
    }

    @Override
    protected void onDestroy() {

        if (mBinder != null && mBinder != Unbinder.EMPTY)
            mBinder.unbind();
        this.mBinder = null;

        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;

        super.onDestroy();
    }


    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public boolean needTitle() {
        return true;
    }

    @Override
    public boolean needStatusBar() {
        return true;
    }


    @Override
    public void getTitleView(TitleView titleView) {

    }


    @Override
    public void getStatusBarHeight(StatusBarHeight statusBar) {

    }


}

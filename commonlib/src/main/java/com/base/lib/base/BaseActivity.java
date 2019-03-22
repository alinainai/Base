package com.base.lib.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;

import com.base.lib.R;
import com.base.lib.lifecycle.ActivityIRxLifecycle;
import com.base.lib.view.StatusBarHeight;
import com.base.lib.view.TitleView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;


/**
 * Activity的基类
 * 依赖activity_base布局
 * activity_base 初始化 @TitleView title和status_bar
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityIRxLifecycle {

    protected AppCompatActivity mContext;
    protected Unbinder mBinder;


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
        lifecycleSubject.onNext(ActivityEvent.CREATE);

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
        ViewStub viewContent = findViewById(R.id.base_fragment_content);
        StatusBarHeight statusBar = findViewById(R.id.v_status_bar);
        TitleView titleView = findViewById(R.id.v_title);

        viewContent.setLayoutResource(initLayoutId(statusBar,titleView));

        //添加ButterKnife绑定
        mBinder = ButterKnife.bind(this, viewContent.inflate());

        initView(savedInstanceState);
        setListener();
        initData();

    }




    /**
     * 设置布局ID
     *
     * @return layoutId
     */
    protected abstract int initLayoutId(StatusBarHeight statusBar ,TitleView titleView);

    /**
     * 初始化布局
     *
     * @param savedInstanceState 保存的数据Bundle
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 设置监听器
     */
    protected void setListener() {

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onDestroy() {

        if (mBinder != null && mBinder != Unbinder.EMPTY)
            mBinder.unbind();
        this.mBinder = null;
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }


    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }


}

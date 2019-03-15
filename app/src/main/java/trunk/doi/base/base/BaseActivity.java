package trunk.doi.base.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import trunk.doi.base.R;
import trunk.doi.base.base.lifecycle.ActivityLifecycleable;
import trunk.doi.base.util.ScreenUtils;

/**
 * Created by  on 2016/5/27 11:08.
 * Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements ActivityLifecycleable {

    protected AppCompatActivity mContext;
    protected Unbinder mBinder;
    protected View mStatusBar;
    FrameLayout viewContent;

    private int mLayoutId;

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return lifecycleSubject;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //允许使用转换动画
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
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(R.color.trans));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        mContext = this;
        //初始化界面内容layout
        this.mLayoutId = initLayoutId();
        setContentView(R.layout.base_page_title);
        initContentView(savedInstanceState);
        setListener();
        initData();

    }

    /**
     * 初始化页面内容
     * savedInstanceState获取照片时使用
     */
    private void initContentView(@Nullable Bundle savedInstanceState) {

        viewContent = findViewById(R.id.base_fragment_content);
        mStatusBar = findViewById(R.id.status_bar);
        View viewPage = View.inflate(mContext, mLayoutId, null);
        viewContent.addView(viewPage);
        //替换状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(mContext);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }
        //添加ButterKnife绑定
        mBinder = ButterKnife.bind(this, viewContent);
        initView(savedInstanceState);

    }


    /**
     * 设置布局ID
     *
     * @return layoutId
     */
    protected abstract int initLayoutId();

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

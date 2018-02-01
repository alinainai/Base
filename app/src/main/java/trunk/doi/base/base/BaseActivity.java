package trunk.doi.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import trunk.doi.base.R;
import trunk.doi.base.dialog.CustomDialog;
import trunk.doi.base.util.ScreenShotListenManager;
import trunk.doi.base.util.ScreenUtils;

/**
 * Created by ly on 2016/5/27 11:08.
 * Activity的基类
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    protected RxAppCompatActivity mContext;
    protected Unbinder mUnbinder;
    public InputMethodManager manager;
    protected View mStatusBar;
    FrameLayout viewContent;

    private int viewContentId;//viewPageLayoutId


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // System.gc();
        ActivityController.getActivityController().addActivity(this);
        //允许使用转换动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        // 不需要toolbar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

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
        this.viewContentId = initLayoutId();
        setContentView(R.layout.base_page_title);
        initContentView(savedInstanceState);
        setListener();
        initData();

    }

    /**
     * 初始化页面内容
     * //savedInstanceState获取照片时使用
     */
    private void initContentView(@Nullable Bundle savedInstanceState) {

        viewContent = findViewById(R.id.base_fragment_content);
        mStatusBar = findViewById(R.id.status_bar);
        View viewPage = View.inflate(mContext, viewContentId, null);
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
        mUnbinder = ButterKnife.bind(this, viewContent);
        initView(savedInstanceState);

    }


    /**
     * 设置布局ID
     * @return
     */
    protected abstract int initLayoutId();

    /**
     * 初始化布局
     * @param savedInstanceState
     */
    protected abstract void initView( @Nullable Bundle savedInstanceState);

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
        ActivityController.getActivityController().removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 点击空白处隐藏软键盘
         */
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                if (manager == null) {
                    manager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                }
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    protected void startActivityAnim(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_left_out);
    }

    protected void finishAnim() {
        super.finish();
        overridePendingTransition(R.anim.base_slide_left_in, R.anim.base_slide_right_out);
    }

    @Override
    public BaseApplication getApplicationContext() {
        return (BaseApplication) super.getApplicationContext();
    }


}

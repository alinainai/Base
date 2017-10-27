package trunk.doi.base.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import trunk.doi.base.dialog.CustomDialog;
import trunk.doi.base.util.ScreenShotListenManager;

/**
 * Created by ly on 2016/5/27 11:08.
 * Activity的基类
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    protected Context mContext;
    protected Unbinder mUnbinder;
//    protected ScreenShotListenManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // System.gc();
        ActivityController.addActivity(this);
        //允许使用转换动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        initLayoutId();
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());

        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        initView( savedInstanceState);
        setListener();
        initData();

//        manager = ScreenShotListenManager.newInstance(context);
//        manager.setListener(
//                new ScreenShotListenManager.OnScreenShotListener() {
//                    public void onShot(String imagePath) {
//
//                        new CustomDialog(BaseActivity.this,imagePath){
//
//                            @Override
//                            public void sure() {
//
//                            }
//                        }.show();
//                    }
//                }
//        );

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
    protected abstract void initView( @Nullable Bundle savedInstanceState);//savedInstanceState获取照片时使用

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
        ActivityController.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(null!=manager){
//            manager.startListen();
//        }
    }


    @Override
    protected void onPause() {
        super.onPause();
//        if(null!=manager){
//            manager.stopListen();
//        }
    }

}

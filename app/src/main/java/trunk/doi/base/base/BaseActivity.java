package trunk.doi.base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ly on 2016/5/27 11:08.
 * Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected Unbinder mUnbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // System.gc();
        ActivityController.addActivity(this);
        //允许使用转换动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        initLayoutId();
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());

        mUnbinder = ButterKnife.bind(this);
        context = this;
        initView( savedInstanceState);
        setListener();
        initData();

    }

    protected abstract int initLayoutId();
    protected abstract void initView( @Nullable Bundle savedInstanceState);//savedInstanceState获取照片时使用
    protected abstract void setListener();
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

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}

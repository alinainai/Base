package com.gas.test.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.test.R;
import com.gas.test.R2;
import com.gas.test.ui.activity.home.di.DaggerHomeComponent;
import com.gas.test.ui.activity.home.mvp.HomeContract;
import com.gas.test.ui.activity.home.mvp.HomePresenter;
import com.gas.test.ui.activity.show.ShowActivity;
import com.gas.test.ui.activity.trans.TransActivity;
import com.lib.commonsdk.constants.RouterHub;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.base.lib.util.Preconditions.checkNotNull;
import static com.gas.test.ui.activity.show.IShowConst.SHOWFRAGMENTTYPE;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 11/27/2019 09:23
 * ================================================
 */
@Route(path = RouterHub.TEST_HOMEACTIVITY)
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {


    private static final int LOADDATA = 0x01;
    private static final long DELAYTIME = 700L;

    @BindView(R2.id.home_recycler)
    RecyclerView mRecyclerView;

    @BindView(R2.id.flContainer)
    FrameLayout flContainer;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    HomeAdapter mAdapter;

    private DelayHandler mHandler;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.test_activity_home_content;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mHandler = new DelayHandler(this);

        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);

        //条目点击
        mAdapter.setOnMultiItemClickListener((viewHolder, item, position, viewType) -> {
                    ArmsUtils.startActivity(new Intent(HomeActivity.this,
                            ShowActivity.class).putExtra(SHOWFRAGMENTTYPE, item.getTag()));
                }
        );
        mAdapter.setOnReloadListener(() -> {
            mPresenter.showTestInfos();
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);

        mHandler.sendEmptyMessageDelayed(LOADDATA, DELAYTIME);

    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }

    @Override
    public Context getWrapContext() {
        return this;
    }

    @Override
    public void loadData() {
        mPresenter.showTestInfos();
    }

    @OnClick({R2.id.tmw_top_show, R2.id.tmw_top_hide, R2.id.tmw_top_show1})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tmw_top_show) {
            startActivity(new Intent(this, TransActivity.class));
        } else if (view.getId() == R.id.tmw_top_show1) {


        } else if (view.getId() == R.id.tmw_top_hide) {


        }
    }


    /**
     * 这里展示静态内部类的的使用
     * Handler 防止内存泄漏
     * 在本类中实现延时{@link HomeActivity#DELAYTIME}时长的数据加载
     */
    private static class DelayHandler extends Handler {

        WeakReference<HomeActivity> mHost;

        DelayHandler(HomeActivity activity) {
            mHost = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOADDATA:
                    if (null != mHost.get())
                        mHost.get().loadData();
                    break;
            }
        }
    }

}

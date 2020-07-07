package com.gas.app.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.componentservice.gank.service.GankInfoService;
import com.base.componentservice.test.service.TestInfoService;
import com.base.componentservice.zhihu.service.ZhihuInfoService;
import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.app.learn.calendarviewV2.CalendarTheme;
import com.gas.app.learn.calendarviewV2.data.CalendarDayModel;
import com.gas.app.learn.calendarviewV2.itemlist.CalendarListAdapter;
import com.gas.app.learn.calendarviewV2.mvp.CalendarSelectDialog;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import com.gas.app.ui.fragment.main.mvp.MainContract;
import com.gas.app.ui.fragment.main.mvp.MainPresenter;
import com.gas.app.R2;
import com.gas.app.ui.fragment.main.di.DaggerMainComponent;

import static com.base.lib.util.Preconditions.checkNotNull;

import com.gas.app.R;

import org.jetbrains.annotations.NotNull;
import org.joda.time.LocalDate;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/30/2019 15:27
 * ================================================
 */
public class MainFragment extends BaseFragment<MainPresenter> implements MainContract.View {


    @BindView(R2.id.btn_load)
    Button mZhihuButton;
    @BindView(R2.id.btn_etr)
    Button mGankButton;
    @BindView(R2.id.btn_thr)
    Button mTestButton;
    @BindView(R2.id.btn_plugin_one)
    Button mPluginOne;
    @BindView(R2.id.btn_plugin_two)
    Button mPluginTwo;
    @BindView(R2.id.btn_etr2)
    Button mGankButton2;


    @Autowired(name = RouterHub.ZHIHU_SERVICE_ZHIHUINFOSERVICE)
    ZhihuInfoService mZhihuInfoService;
    @Autowired(name = RouterHub.GANK_SERVICE_GANKINFOSERVICE)
    GankInfoService mGankInfoService;
    @Autowired(name = RouterHub.TEST_SERVICE_TESTINFOSERVICE)
    TestInfoService mTestInfoService;




    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);
        loadModuleInfo();

    }


    @Override
    public void setData(@Nullable Object data) {

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

    }


    private void loadModuleInfo() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mZhihuInfoService != null) {
            mZhihuButton.setText(mZhihuInfoService.getInfo().getName());
        }
        if (mGankInfoService != null) {
            mGankButton.setText(mGankInfoService.getInfo().getName());
            mGankButton2.setText(mGankInfoService.getInfo().getName2());
        }

        if (mTestInfoService != null) {
            mTestButton.setText(mTestInfoService.getInfo().getName());
        }

    }

    @OnClick({R.id.btn_load, R.id.btn_etr, R.id.btn_plugin_one,
            R2.id.btn_etr2, R.id.btn_plugin_two, R.id.btn_thr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load://组件化模块一
                Utils.navigation(getActivity(), RouterHub.ZHIHU_HOMEACTIVITY);
                break;
            case R.id.btn_etr://组件化模块二
                Utils.navigation(getActivity(), RouterHub.GANK_MAINACTIVITY);
                break;
            case R.id.btn_etr2://组件化模块二
                Utils.navigation(getActivity(), RouterHub.GANK_HOMEACTIVITY);
                break;
            case R.id.btn_thr://组件化模块三
                Utils.navigation(getActivity(), RouterHub.TEST_HOMEACTIVITY);
                break;
            case R.id.btn_plugin_one://插件一

                CalendarSelectDialog  dialog=new CalendarSelectDialog(getActivity(),  CalendarTheme.Gold.INSTANCE, new CalendarListAdapter.OnDayClickListener() {
                    @Override
                    public void onDayItemClick(@NotNull CalendarDayModel date) {

                    }
                });

            dialog.showSelect(LocalDate.now());

                break;
            case R.id.btn_plugin_two://插件二
                break;
        }
    }

}

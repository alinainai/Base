package trunk.doi.base.ui.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.componentservice.gank.service.GankInfoService;
import com.base.componentservice.test.service.TestInfoService;
import com.base.componentservice.zhihu.service.ZhihuInfoService;
import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.lib.commonsdk.constants.RouterHub;
import com.lib.commonsdk.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.R2;


/**
 * Created by
 * 首页的fragment  (首页第二个栏目)
 */
public class MainFragment extends BaseFragment {


    public static final String TAG = "MainFragment";


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


    public MainFragment() {
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_blank, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);
        loadModuleInfo();

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


    @Override
    public void setData(@Nullable Object data) {

    }


    @OnClick({R.id.btn_load, R.id.btn_etr, R.id.btn_plugin_one,
            R2.id.btn_etr2,
            R.id.btn_plugin_two, R.id.btn_thr})
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
                break;
            case R.id.btn_plugin_two://插件二
                break;
        }
    }


}

package trunk.doi.base.ui.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.componentservice.zhihu.service.ZhihuInfoService;
import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.lib.commonsdk.core.RouterHub;
import com.lib.commonsdk.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.R;
import trunk.doi.base.ui.MainActivity;
import trunk.doi.base.util.GasUtils;


/**
 * Created by
 * 首页的fragment  (首页第二个栏目)
 */
public class MainFragment extends BaseFragment {


    public static final String TAG = "MainFragment";
    @BindView(R.id.tv_show)
    TextView tvShow;

    @BindView(R.id.btn_load)
    TextView mZhihuButton;

    private Disposable mDisposable;

    @Autowired(name = RouterHub.ZHIHU_SERVICE_ZHIHUINFOSERVICE)
    ZhihuInfoService mZhihuInfoService;


    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        loadZhihuInfo();

    }

    private void loadZhihuInfo(){
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mZhihuInfoService == null) {
            mZhihuButton.setEnabled(false);
            return;
        }
        mZhihuButton.setText(mZhihuInfoService.getInfo().getName());
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_blank;
    }


    @OnClick({R.id.btn_load, R.id.btn_etr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                Utils.navigation(getActivity(), RouterHub.ZHIHU_HOMEACTIVITY);
                break;
            case R.id.btn_etr:

                tvShow.setText("16dp的px值"+GasUtils.dpTopx(mContext, 16));
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null && mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mDisposable = null;
        super.onDestroy();

    }
}

package com.gas.test.ui.fragment.timedown;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.test.R;
import com.gas.test.ui.fragment.timedown.di.DaggerTimeDownComponent;
import com.gas.test.ui.fragment.timedown.mvp.TimeDownContract;
import com.gas.test.ui.fragment.timedown.mvp.TimeDownPresenter;
import com.gas.test.widget.TimeDownView;
import com.gas.test.widget.banner.BottomTimeDownPromptBanner;
import com.gas.test.widget.banner.TimeDownPromptBanner;

import butterknife.BindView;
import butterknife.OnClick;

import static com.base.lib.util.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 倒计时布局测试
 * <p>
 * Created by GasMvpFragment on 03/04/2020 17:07
 * ================================================
 */
public class TimeDownFragment extends BaseFragment<TimeDownPresenter> implements TimeDownContract.View {


    private static final long TWO_DAY_TIME = 2 * 24 * 60 * 60 * 1000L;

    @BindView(R.id.tdv_1)
    TimeDownView tdv1;

    @BindView(R.id.top_banner)
    TimeDownPromptBanner topBanner;

    @BindView(R.id.bottom_banner)
    BottomTimeDownPromptBanner bottomBanner;


    private TimeDownPromptBanner.BannerConfig mTopBannerConfig;
    private TimeDownPromptBanner.BannerConfig mBottomBannerConfig;

    public static TimeDownFragment newInstance() {
        TimeDownFragment fragment = new TimeDownFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTimeDownComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_time_down, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTopBanner();
    }


    private void initTopBanner() {

        mTopBannerConfig = new TimeDownPromptBanner
                .BannerConfig("顶部弹窗")
                .setSubTitle("顶部副标题")
                .setIconResId(R.drawable.public_ic_launcher)
                .setBgResId(R.color.public_color_primary)
                .setCloseResId(R.mipmap.lib_title_bar_back)
                .setOnCloseClick(() -> showMessage("关闭点击"))
                .setViewOnClick(() -> showMessage("条目点击"))
                .setShowDuration(6)
                .setTitleColor(R.color.public_color_accent)
                .setSubTitleOnClick(() -> showMessage("副标题点击"));

        mBottomBannerConfig = new TimeDownPromptBanner
                .BannerConfig("底部弹窗")
                .setSubTitle("底部副标题")
                .setIconResId(R.drawable.public_ic_launcher)
                .setCloseResId(R.mipmap.lib_title_bar_back)
                .setOnCloseClick(() -> showMessage("底部关闭点击"))
                .setViewOnClick(() -> showMessage("底部条目点击"))
                .setShowDuration(6)
                .setTag("bottom")
                .setSubTitleOnClick(() -> showMessage("底部副标题点击"));


    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    @OnClick({R.id.tmw_start, R.id.tmw_end, R.id.tmw_top_show, R.id.tmw_top_hide,
            R.id.tmw_top_show1,R.id.tmw_bottom_show,R.id.tmw_bottom_show1,R.id.tmw_bottom_hide})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tmw_start) {

            tdv1.setTimeDown(TWO_DAY_TIME);

        } else if (view.getId() == R.id.tmw_end) {

            tdv1.stopDownTime();

        } else if (view.getId() == R.id.tmw_top_show) {

            topBanner.show(mTopBannerConfig);

        } else if (view.getId() == R.id.tmw_top_hide) {

            topBanner.dismiss();

        }else if (view.getId() == R.id.tmw_top_show1) {

            topBanner.show(mBottomBannerConfig);

        }else if (view.getId() == R.id.tmw_bottom_show) {

            bottomBanner.show(mTopBannerConfig);

        } else if (view.getId() == R.id.tmw_bottom_show1) {

            bottomBanner.show(mBottomBannerConfig);

        }else if (view.getId() == R.id.tmw_bottom_hide) {
            bottomBanner.dismiss();
        }
    }


}

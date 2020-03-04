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

import butterknife.BindView;
import butterknife.OnClick;

import static com.base.lib.util.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 03/04/2020 17:07
 * ================================================
 */
public class TimeDownFragment extends BaseFragment<TimeDownPresenter> implements TimeDownContract.View {


    private static final long TWO_DAY_TIME = 2 * 24 * 60 * 60 * 1000L;
    @BindView(R.id.tdv_1)
    TimeDownView tdv1;


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

    @OnClick({R.id.tmw_start, R.id.tmw_end})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tmw_start) {
            tdv1.setTimeDown(TWO_DAY_TIME);
        } else if (view.getId() == R.id.tmw_end) {
            tdv1.stopDownTime();
        }
    }


}

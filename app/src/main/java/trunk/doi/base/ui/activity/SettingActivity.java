package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.ui.activity.test.MainActivity;
import trunk.doi.base.ui.activity.test.shop.ShopActivity;
import trunk.doi.base.view.pullrefresh.CustomRefreshHeader;
import trunk.doi.base.view.pullrefresh.RefreshLayout;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;
    @BindView(R.id.setting_feedback_tv)
    TextView settingFeedbackTv;
    @BindView(R.id.update_version_num_tv)
    TextView updateVersionNumTv;
    @BindView(R.id.refresh)
    RefreshLayout refreshLayout;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        mainCartTitle.setText("设置");

        refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 延迟3秒后刷新成功
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();

                    }
                }, 1000);
            }
        });

        CustomRefreshHeader header  = new CustomRefreshHeader(this);
        refreshLayout.setRefreshHeader(header);
        refreshLayout.autoRefresh();

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.ll_back, R.id.setting_feedback_tv, R.id.setting_recommend_friends_tv, R.id.setting_service_terms_tv, R.id.setting_about_us_tv, R.id.setting_cache_size_tv, R.id.setting_check_update_rl, R.id.setting_exit_login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.setting_feedback_tv:
                startActivity(new Intent(SettingActivity.this,GuesterActivity.class));
                break;
            case R.id.setting_recommend_friends_tv:
                startActivity(new Intent(SettingActivity.this,ShopActivity.class));
                break;
            case R.id.setting_service_terms_tv:
                startActivity(new Intent(SettingActivity.this,MainActivity.class));
                break;
            case R.id.setting_about_us_tv:
                break;
            case R.id.setting_cache_size_tv:
                break;
            case R.id.setting_check_update_rl:
                break;
            case R.id.setting_exit_login_btn:
                break;
        }
    }
}

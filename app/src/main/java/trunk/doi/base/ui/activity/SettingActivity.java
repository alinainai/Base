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

import trunk.doi.base.base.RxBus;
import trunk.doi.base.bean.rxmsg.MainEvent;
import trunk.doi.base.dialog.AlertDialog;
import trunk.doi.base.dialog.ApkDownDialog;
import trunk.doi.base.util.ToastUtil;
import trunk.doi.base.view.TitleView;
import trunk.doi.base.view.pullrefresh.CustomRefreshHeader;
import trunk.doi.base.view.pullrefresh.RefreshLayout;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TitleView tvTitle;
    @BindView(R.id.setting_feedback_tv)
    TextView settingFeedbackTv;
    @BindView(R.id.update_version_num_tv)
    TextView updateVersionNumTv;

    private String fileurl = "https://dl.gqget.com/dl/pkg/apk/get_android/GqiP2p.apk";


    @Override
    protected int initLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {


    }

    @Override
    protected void setListener() {

        tvTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAnim();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        finishAnim();
    }

    @OnClick({R.id.setting_feedback_tv, R.id.setting_recommend_friends_tv, R.id.setting_service_terms_tv, R.id.setting_about_us_tv, R.id.setting_cache_size_tv, R.id.setting_check_update_rl, R.id.setting_exit_login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_feedback_tv:
                startActivityAnim(new Intent(mContext, FeedBackActivity.class));
                break;
            case R.id.setting_recommend_friends_tv:
                break;
            case R.id.setting_service_terms_tv:
                break;
            case R.id.setting_about_us_tv:
                startActivityAnim(new Intent(mContext, AboutActivity.class));
                break;
            case R.id.setting_cache_size_tv:
                break;
            case R.id.setting_check_update_rl:

                new ApkDownDialog(mContext, fileurl, false) {

                    @Override
                    public void sure() {
                        ToastUtil.show(mContext,"下载完成");
                    }
                }.show();

                break;
            case R.id.setting_exit_login_btn:

                new AlertDialog(mContext).builder()
                        .setMsg("是否退出登录")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RxBus.getDefault().post(new MainEvent(0,""));
                                finishAnim();
                            }
                        }).setNegativeButton("取消", null).show();


                break;
        }
    }
}

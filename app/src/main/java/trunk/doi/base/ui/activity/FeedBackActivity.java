package trunk.doi.base.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.view.TitleView;

/**
 * Created by ly on 2016/6/23.
 * 意见反馈详情页
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.feed_back_input_ed)
    EditText feedBackInputEd;//意见反馈输入
    @BindView(R.id.feed_back_phone_input_ed)
    EditText feedBackPhoneInputEd;//手机号输入

    @BindView(R.id.tv_title)
    TitleView tvTitle;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListener() {

        tvTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAnim();
            }
        });

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.feed_back_input_ed, R.id.feed_back_phone_input_ed, R.id.feed_back_submit_btn,R.id.rl_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feed_back_input_ed://意见反馈
                break;
            case R.id.feed_back_phone_input_ed://手机号
                break;
            case R.id.feed_back_submit_btn://提交
                break;
            case R.id.rl_type://反馈类型

                break;

        }
    }
    @Override
    public void onBackPressed() {
       finishAnim();
    }

}

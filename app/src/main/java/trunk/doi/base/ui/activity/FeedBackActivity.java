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

/**
 * Created by ly on 2016/6/23.
 * 意见反馈详情页
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.feed_back_input_ed)
    EditText feedBackInputEd;//意见反馈输入
    @BindView(R.id.feed_back_phone_input_ed)
    EditText feedBackPhoneInputEd;//手机号输入
    @BindView(R.id.feed_back_submit_btn)
    Button feedBackSubmitBtn;//提交按钮
    @BindView(R.id.main_cart_title)
    TextView mainCartTitle;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mainCartTitle.setText("意见反馈");
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.feed_back_input_ed, R.id.feed_back_phone_input_ed, R.id.feed_back_submit_btn,R.id.rl_type,R.id.ll_back})
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
            case R.id.ll_back://返回
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}

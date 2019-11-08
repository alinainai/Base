package com.gas.zhihu.detial;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gas.zhihu.R;
import com.gas.zhihu.R2;
import com.gas.zhihu.app.ZhihuConstants;
import com.lib.commonsdk.constants.Constants;
import com.lib.commonsdk.constants.RouterHub;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = RouterHub.ZHIHU_DETAILACTIVITY)
public class DetailsActivity extends AppCompatActivity {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_url)
    TextView tvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_details);
        ButterKnife.bind(this);
        String title=getIntent().getStringExtra(Constants.PUBLIC_TITLE);
        String url=getIntent().getStringExtra(Constants.PUBLIC_URL);
        tvTitle.setText(getResources().getString(R.string.zhihu_title_format,title));
        tvUrl.setText(getResources().getString(R.string.zhihu_url_format,url));
    }
}

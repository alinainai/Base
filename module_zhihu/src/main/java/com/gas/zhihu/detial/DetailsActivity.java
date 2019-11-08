package com.gas.zhihu.detial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gas.zhihu.R;
import com.lib.commonsdk.consants.RouterHub;


@Route(path = RouterHub.ZHIHU_DETAILACTIVITY)
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_activity_details);
    }
}

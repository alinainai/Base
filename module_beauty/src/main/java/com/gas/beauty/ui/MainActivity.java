package com.gas.beauty.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gas.beauty.R;
import com.gas.beauty.article.ArticleFragment;
import com.gas.beauty.ui.article.classify.ClassifyFragment;
import com.lib.commonsdk.constants.RouterHub;

@Route(path = RouterHub.GANK_MAINACTIVITY)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
//        Fragment fragment= ClassifyFragment.newInstance("all");
        Fragment fragment= ArticleFragment.newInstance();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,fragment)
                    .commitNow();

            fragment.setUserVisibleHint(true);

        }
    }
}

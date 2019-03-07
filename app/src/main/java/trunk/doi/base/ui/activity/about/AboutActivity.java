package trunk.doi.base.ui.activity.about;

import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.base.mvp.BaseMvpActivity;
import trunk.doi.base.view.TitleView;


/**
 * Created by ly on 2016/6/22.
 * 设置
 */
public class AboutActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TitleView tvTitle;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_about;
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

    @Override
    public void onBackPressed() {
        finishAnim();
    }


}

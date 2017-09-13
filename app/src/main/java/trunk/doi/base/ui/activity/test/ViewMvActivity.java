package trunk.doi.base.ui.activity.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.ToastUtils;

public class ViewMvActivity extends BaseActivity {

    @BindView(R.id.cs_view)
    CustomImageView csView;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_view_mv;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        csView.setOnImageMoveListener(new CustomImageView.onImageMoveListener() {
            @Override
            public void onViewClick() {
                ToastUtils.showShort(context,"click");
            }

            @Override
            public void onViewClose() {
                ToastUtils.showShort(context,"close");
            }
        });

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }


}

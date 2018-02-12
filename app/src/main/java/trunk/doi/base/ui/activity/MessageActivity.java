package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.ToastUtil;


/**
 * Created by ly on 2016/6/22.
 * 收藏界面
 */
public class MessageActivity extends BaseActivity {


    @BindView(R.id.tv_bar_1)
    TextView tvBar1;
    @BindView(R.id.tv_bar_2)
    TextView tvBar2;
    @BindView(R.id.tv_bar_3)
    TextView tvBar3;
    @BindView(R.id.tv_bar_4)
    TextView tvBar4;

    private int numSelector=1;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (savedInstanceState != null && savedInstanceState.containsKey("selector")) {
            numSelector =  savedInstanceState.getInt("selector");
        }


    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

        setTextColor(1);

    }




    @OnClick({R.id.ll_bar_1, R.id.ll_bar_2, R.id.ll_bar_3, R.id.ll_bar_4, R.id.tv_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_bar_1:
                if(numSelector==1){
                    ToastUtil.show(mContext,"选中1");
                    return;
                }
                numSelector=1;
                setTextColor(1);
                break;
            case R.id.ll_bar_2:
                numSelector=2;
                setTextColor(2);
                break;
            case R.id.ll_bar_3:
                numSelector=3;
                setTextColor(3);
                break;
            case R.id.ll_bar_4:
                numSelector=4;
                setTextColor(4);
                break;
            case R.id.tv_click:
                startActivityAnim(new Intent(mContext,MineDetailActivity.class));
                break;
        }
    }

    private void setTextColor(int num){

        switch (num){

            case 1:
                tvBar1. setTextColor(getResources().getColor(R.color.cff3e19));
                tvBar2. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar3. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar4. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                break;
            case 2:
                tvBar2. setTextColor(getResources().getColor(R.color.cff3e19));
                tvBar1. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar3. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar4. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                break;
            case 3:
                tvBar3. setTextColor(getResources().getColor(R.color.cff3e19));
                tvBar2. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar4. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar1. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                break;
            case 4:
                tvBar4. setTextColor(getResources().getColor(R.color.cff3e19));
                tvBar3. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar2. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                tvBar1. setTextColor(getResources().getColor(R.color.bottom_tab_text_color_nor));
                break;

        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selector", numSelector);
    }

    @Override
    public void onBackPressed() {
        finishAnim();
    }
}

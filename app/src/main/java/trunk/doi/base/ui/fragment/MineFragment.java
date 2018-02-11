package trunk.doi.base.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.ui.activity.CollectionActivity;
import trunk.doi.base.ui.activity.LoginActivity;
import trunk.doi.base.ui.activity.SettingActivity;
import trunk.doi.base.util.ScreenUtils;
import trunk.doi.base.view.CircleImageView;

/**
 * Created by ly on 2016/5/30 11:05.
 * 首页的fragment  (首页第四个栏目)
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.status_bar)
    View mStatusBar;
    public static final String TAG = "MineFragment";
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;

    private List<String> strs=new ArrayList<>();
    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBar.setVisibility(View.VISIBLE);
            mStatusBar.getLayoutParams().height = ScreenUtils.getStatusHeight(mContext);
            mStatusBar.setLayoutParams(mStatusBar.getLayoutParams());
        } else {
            mStatusBar.setVisibility(View.GONE);
        }

        srl_refresh.setColorSchemeResources(R.color.cff3e19);
        srl_refresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl_refresh.setRefreshing(false);
                    }
                },700);
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }




    @OnClick({R.id.img_message, R.id.img_set, R.id.iv_head, R.id.tv_name, R.id.tv_phone,R.id.ll_wdtyj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_message:

                break;
            case R.id.img_set:
                startActivityAnim(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.iv_head:
                startActivityAnim(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.tv_name:

                break;
            case R.id.tv_phone:
                break;
            case R.id.ll_wdtyj:
                startActivityAnim(new Intent(mContext, CollectionActivity.class));
                break;
        }
    }
}

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
import butterknife.OnClick;
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


    public static final String TAG = "MineFragment";


    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }





}

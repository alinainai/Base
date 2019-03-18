package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.base.lib.base.BaseFragment;

import trunk.doi.base.R;

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

package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;


/**
 * Created by ly on 2016/5/30 11:07.
 * 我的fragment
 */
public class MineFragment extends BaseFragment {
    public static final String TAG = "MineFragment";



    public static MineFragment newInstance() {
        return new MineFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStae) {

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }



}

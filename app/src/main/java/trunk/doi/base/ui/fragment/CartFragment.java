package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;

/**
 * Created by li on 2016/6/29.
 */
public class CartFragment extends BaseFragment {

    public static final String TAG = "CartFragment";

    public static CartFragment newInstance() {
        return new CartFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {}

    @Override
    public void initData(Bundle savedInstanceState) {

    }



}

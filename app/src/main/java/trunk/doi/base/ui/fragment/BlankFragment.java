package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;


public class BlankFragment extends BaseFragment{


    public static final String TAG = "BlankFragment";




    public BlankFragment() {
    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_about;
    }


    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }








}

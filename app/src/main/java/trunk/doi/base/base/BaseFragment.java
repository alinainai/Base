package trunk.doi.base.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import trunk.doi.base.R;

/**
 * Created by ly on 2016/5/30 11:00.
 * fragment基类
 */
public abstract class BaseFragment extends RxFragment {
    protected Context mContext;
    public View rootView;
    protected Unbinder mUnbinder;

    protected abstract int initLayoutId();


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(initLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initView( inflater, container,savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener(view, savedInstanceState);
    }

    public abstract void initView(LayoutInflater inflater, ViewGroup container,@Nullable Bundle savedInstanceState);

    public abstract void setListener(View view, @Nullable Bundle savedInstanceState);

    public abstract void initData(@Nullable Bundle savedInstanceState);


    protected void startActivityAnim(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_left_out);
    }

    protected void finishAnim() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.base_slide_left_in, R.anim.base_slide_right_out);
    }




}

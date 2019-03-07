package trunk.doi.base.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import trunk.doi.base.R;

/**
 * Created by
 * fragment基类
 */
public abstract class BaseFragment extends RxFragment {
    protected Context mContext;
    public View rootView;
    protected Unbinder mUnbinder;

    protected abstract int initLayoutId();

    public abstract void initView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState);

    public void setListener() {
    }

    public void initData() {
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        Log.e("BaseFragment","onActivityCreated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        Log.e("BaseFragment","onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("BaseFragment","onCreateView");
        rootView = inflater.inflate(initLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("BaseFragment","onViewCreated");
        setListener();
    }


    protected void startActivityAnim(Intent intent) {
        super.startActivity(intent);
        //noinspection ConstantConditions
        getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_left_out);
    }

    protected void finishAnim() {
        //noinspection ConstantConditions
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.base_slide_left_in, R.anim.base_slide_right_out);
    }


}

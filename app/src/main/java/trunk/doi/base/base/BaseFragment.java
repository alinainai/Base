package trunk.doi.base.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private Unbinder mBinder;
    protected  final String TAG = this.getClass().getSimpleName();

    protected abstract int initLayoutId();

    public abstract void initView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(initLayoutId(), container, false);
        mBinder = ButterKnife.bind(this, rootView);
        initView(inflater, container, savedInstanceState);
        return rootView;
    }


    @Override
    public void onDestroy() {
        mBinder.unbind();
        super.onDestroy();
    }








}

package trunk.doi.base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ly on 2016/5/30 11:00.
 * fragment基类
 */
public abstract class BaseFragment extends RxFragment {
    protected Context context;
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
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // initLayoutId();
        rootView = inflater.inflate(initLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initView( inflater, container,savedInstanceState);
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
//        ViewGroup parent = (ViewGroup) rootView.getParent();
//        if (parent != null) {
//            parent.removeView(rootView);
//        }
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




}

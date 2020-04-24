package com.gas.test.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.test.R;
import com.gas.test.R2;
import com.gas.test.ui.fragment.adapter.di.DaggerAdapterComponent;
import com.gas.test.ui.fragment.adapter.mvp.AdapterContract;
import com.gas.test.ui.fragment.adapter.mvp.AdapterPresenter;
import com.gas.test.widget.RecyclerStickHeaderHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.base.lib.util.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 11/29/2019 16:48
 * ================================================
 */
public class AdapterFragment extends BaseFragment<AdapterPresenter> implements AdapterContract.View , SwipeRefreshLayout.OnRefreshListener {

    @BindView(R2.id.test_adapter_recycler)
    RecyclerView mRecyclerView;
    @BindView(R2.id.adapter_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R2.id.flContainer)
    FrameLayout flContainer;



    @Inject
    SimpleMultiAdapter mAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    public static AdapterFragment newInstance() {
        AdapterFragment fragment = new AdapterFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAdapterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_adapter, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        mRefresh.setColorSchemeResources(R.color.public_white);
        mRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.public_black));
        mRefresh.setOnRefreshListener(this);

        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);

        mAdapter.setOnLoadMoreListener(isReload -> mPresenter.loadData(false));

        mAdapter.setOnReloadListener(() -> {
            mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
            mPresenter.loadData(true);
        });
        mAdapter.setOnMultiItemClickListener((viewHolder, data, position, viewType) ->{
            mAdapter.remove(position);
            showMessage(data);
        });
        mRecyclerView.setAdapter(mAdapter);
        new RecyclerStickHeaderHelper(mRecyclerView,flContainer,mAdapter.getHeaderStickType());
        View header = LayoutInflater.from(mContext).inflate(R.layout.test_header_view, null);
        mAdapter.addHeaderView(header);
        mPresenter.initPresent();


    }


    @Override
    public void setData(@Nullable Object data) {



    }

    @Override
    public void showLoading() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @OnClick({R2.id.btn_add_one, R2.id.btn_add_multi})
    public void onViewClicked(View view) {

        if (view.getId() == R.id.btn_add_one) {
            mPresenter.insertData(true);
        } else if (view.getId() == R.id.btn_add_multi) {
            mPresenter.insertData(false);
        }
    }

    @Override
    public Context getWrapContent() {
        return getContext();
    }

    @Override
    public void onRefresh() {
        mPresenter.loadData(true);
    }
}

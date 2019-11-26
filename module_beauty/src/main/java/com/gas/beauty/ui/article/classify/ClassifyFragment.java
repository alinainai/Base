package com.gas.beauty.ui.article.classify;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.base.lib.base.LazyLoadFragment;
import com.base.lib.di.component.AppComponent;
import com.base.paginate.interfaces.EmptyInterface;
import com.gas.beauty.R;
import com.gas.beauty.R2;
import com.gas.beauty.bean.GankItemBean;
import com.gas.beauty.ui.article.classify.di.DaggerClassifyComponent;
import com.gas.beauty.ui.article.classify.mvp.ClassifyContract;
import com.gas.beauty.ui.article.classify.mvp.ClassifyPresenter;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class ClassifyFragment extends LazyLoadFragment<ClassifyPresenter> implements ClassifyContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String SUB_TYPE = "SUB_TYPE";
    private int mPage = 1;//页数
    private final static int PAGE_COUNT = 10;//每页条数
    private String mSubtype;//分类


    @BindView(R2.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R2.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ClassifyAdapter mClassifyAdapter;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

        DaggerClassifyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.public_refresh_recycler, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        assert getArguments() != null;
        mSubtype = getArguments().getString(SUB_TYPE);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //刷新控件
        mSwipeRefreshLayout.setColorSchemeResources(R.color.public_black);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.public_white));

        //条目点击
        mClassifyAdapter.setOnMultiItemClickListener((viewHolder, item, position, viewType) -> {
                }
//                mContext.startActivity(new Intent(mContext, WebViewActivity.class)
//                        .putExtra("title", item.getDesc())
//                        .putExtra("url", item.getUrl()))
        );

        mClassifyAdapter.setOnReloadListener(() -> {
            mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
            loadData();
        });

        mClassifyAdapter.setOnLoadMoreListener(isReload -> loadData());
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setAdapter(mClassifyAdapter);
        mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_LOADING);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void loadData() {
        assert mPresenter != null;
        mPresenter.getGankItemData(String.format(Locale.CHINA, "data/%s/" + PAGE_COUNT + "/%d", mSubtype, mPage));
    }


    public static ClassifyFragment newInstance(String subtype) {
        ClassifyFragment fragment = new ClassifyFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onSuccess(List<GankItemBean> data) {


        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (data.size() > 0) {

            if (mPage == 1) {
                mClassifyAdapter.setNewData(data);
            } else {
                mClassifyAdapter.setLoadMoreData(data);
            }
            if (data.size() < PAGE_COUNT) {//如果小于10个
                mClassifyAdapter.loadEnd();
            }
            mPage++;
        } else {
            if (mPage > 1) {
                mClassifyAdapter.showFooterFail();
            } else {
                mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_FAIL);
            }
        }

    }

    @Override
    public Context getWrapContext() {
        return mContext;
    }

    @Override
    public void onError() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mPage > 1) {
            mClassifyAdapter.showFooterFail();
        } else {
            mClassifyAdapter.setEmptyView(EmptyInterface.STATUS_FAIL);
        }
    }


    @Override
    protected void lazyLoadData() {
        loadData();
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        loadData();
    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}

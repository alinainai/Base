package trunk.doi.base.ui.fragment.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.adapter.GankItemAdapter;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.ui.activity.utils.WebViewActivity;
import trunk.doi.base.ui.fragment.classify.contract.ClassifyContract;
import trunk.doi.base.ui.fragment.classify.contract.ClassifyModle;
import trunk.doi.base.ui.fragment.classify.contract.ClassifyPresenter;
import trunk.doi.base.ui.fragment.classify.di.DaggerClassifyComponent;
import trunk.doi.base.util.WrapContentLinearLayoutManager;

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class GankItemFragment extends BaseFragment<ClassifyPresenter> implements ClassifyContract.View {

    private static final String SUB_TYPE = "SUB_TYPE";
    private int mPage = 1;//页数
    private final static int PAGE_COUNT = 10;//每页条数
    private String mSubtype;//分类
    private GankItemAdapter mGankItemAdapter;//适配器


    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条
    @BindView(R.id.view_net_error)
    android.view.View view_net_error;
    @BindView(R.id.view_load)
    android.view.View view_load;


    private android.view.View mLoadingView;
    private android.view.View mLoadEmpty;

    private boolean isFirdtLoad;


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
    public int initLayoutId() {
        return R.layout.layout_base_reload;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //mRecyclerView
        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //刷新控件
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.cff3e19));
        mGankItemAdapter = new GankItemAdapter(mContext, new ArrayList<>(), true);
        //RecyclerView 基础布局
        mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, (ViewGroup) mRecyclerView.getParent(), false);
        mLoadEmpty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_data, (ViewGroup) mRecyclerView.getParent(), false);
        //RecyclerView  footerView  布局
        mGankItemAdapter.setLoadingView(R.layout.view_loading);
        mGankItemAdapter.setLoadFailedView(R.layout.view_error);
        mGankItemAdapter.setLoadEndView(R.layout.view_nom);

        //加载失败布局
        android.view.View.OnClickListener retryListener = view -> {
            mPage = 1;
            if (view_net_error.getVisibility() == android.view.View.VISIBLE) {
                view_net_error.setVisibility(android.view.View.GONE);
            }
            view_load.setVisibility(android.view.View.VISIBLE);
            loadData();
        };
        //条目点击
        mGankItemAdapter.setOnItemClickListener((viewHolder, gankItemData, position) -> mContext.startActivity(new Intent(mContext, WebViewActivity.class)
                .putExtra("title", gankItemData.getDesc())
                .putExtra("url", gankItemData.getUrl())));
        //加载更多
        mGankItemAdapter.setOnLoadMoreListener(isReload -> loadData());
        //刷新
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPage = 1;
            loadData();
        });
        view_net_error.findViewById(R.id.tv_retry).setOnClickListener(retryListener);
        mLoadEmpty.findViewById(R.id.tv_click).setOnClickListener(retryListener);

        mSwipeRefreshLayout.setEnabled(false);
        assert getArguments() != null;
        mSubtype = getArguments().getString(SUB_TYPE);
        mRecyclerView.setAdapter(mGankItemAdapter);
        //RecyclerView  首次Loading布局
        mGankItemAdapter.setEmptyView(mLoadingView);

    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        mPresenter.getGankItemData(String.format(Locale.CHINA, "data/%s/10/%d", mSubtype, mPage));
    }


    public static GankItemFragment newInstance(String subtype) {
        GankItemFragment fragment = new GankItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onSuccess(List<GankItemData> data) {

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (view_load.getVisibility() == android.view.View.VISIBLE) {
            view_load.setVisibility(android.view.View.GONE);
        }
        if (isFirdtLoad) {
            mGankItemAdapter.removeEmptyView();
            isFirdtLoad = false;
        }
        mSwipeRefreshLayout.setEnabled(true);

        if (data.size() > 0) {

            if (mPage == 1) {
                mGankItemAdapter.reset();
                mGankItemAdapter.setNewData(data);
            } else {
                mGankItemAdapter.setLoadMoreData(data);
            }
            if (data.size() < PAGE_COUNT) {//如果小于10个
                mGankItemAdapter.loadEnd();
            }
            mPage++;
        } else {
            if (mPage > 1) {
                mGankItemAdapter.loadFailed();
            } else {
                mGankItemAdapter.reset();
                mGankItemAdapter.setReloadView(mLoadEmpty);
                mSwipeRefreshLayout.setEnabled(false);
            }
        }

    }

    @Override
    public void onError() {
        mSwipeRefreshLayout.setEnabled(true);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (view_load.getVisibility() == android.view.View.VISIBLE) {
            view_load.setVisibility(android.view.View.GONE);
        }
        if (isFirdtLoad) {
            mGankItemAdapter.removeEmptyView();
            isFirdtLoad = false;
        }
        if (mPage > 1) {
            mGankItemAdapter.loadFailed();
        } else {
            mGankItemAdapter.reset();
            view_net_error.setVisibility(android.view.View.VISIBLE);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }


}

package trunk.doi.base.ui.fragment.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.adapter.rvadapter.OnItemClickListeners;
import trunk.doi.base.adapter.rvadapter.OnLoadMoreListener;
import trunk.doi.base.adapter.rvadapter.ViewHolder;
import trunk.doi.base.base.mvp.BaseMvpFragment;
import trunk.doi.base.ui.activity.utils.WebViewActivity;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:28
 */
public class GankItemFragment extends BaseMvpFragment<GankItemView, GankItemPresenter> implements GankItemView, SwipeRefreshLayout.OnRefreshListener {

    private int PAGE_COUNT = 1;//页数
    private String mSubtype;//分类
    private int mTempPageCount = 2;
    private GankItemAdapter mGankItemAdapter;//适配器
    private boolean isLoadMore;//是否还有

    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条

    @Override
    protected GankItemPresenter initPresenter() {
        return new GankItemPresenter();
    }

    /**
     * 加载数据
     */
    @Override
    protected void fetchData() {
        mPresenter.getGankItemData("data/" + mSubtype + "/10/" + PAGE_COUNT);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_type_item_layout;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mGankItemAdapter = new GankItemAdapter(context, new ArrayList<GankItemData>(), true);
        mGankItemAdapter.setLoadingView(R.layout.view_loading);
        mGankItemAdapter.setOnItemClickListener(new OnItemClickListeners<GankItemData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, GankItemData gankItemData, int position) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", gankItemData.getUrl());
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        mGankItemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                if (PAGE_COUNT == mTempPageCount && !isReload) {
                    return;
                }
                isLoadMore = true;
                PAGE_COUNT = mTempPageCount;
                fetchData();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mGankItemAdapter);

    }


    @Override
    public void setListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() == null) {
            return;
        }
        mSubtype = getArguments().getString(SUB_TYPE);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white, R.color.yellow, R.color.pink);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.cff3e19));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

    }

    @Override
    public void onSuccess(List<GankItemData> data) {
        if (isLoadMore) {
            if (data.size() == 0) {
                mGankItemAdapter.setLoadEndView(R.layout.view_nom);
            } else {
                mGankItemAdapter.setLoadMoreData(data);
                mTempPageCount++;
            }
        } else {
            mGankItemAdapter.setNewData(data);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError() {
        if (isLoadMore) {
            mGankItemAdapter.setLoadFailedView(R.layout.view_error);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public static GankItemFragment newInstance(String subtype) {
        GankItemFragment fragment = new GankItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onRefresh() {
        isLoadMore = false;
        PAGE_COUNT = 1;
        fetchData();
    }
}

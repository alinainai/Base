package trunk.doi.base.ui.fragment.classify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.base.lib.base.LazyLoadFragment;
import com.base.lib.di.component.AppComponent;
import com.base.paginate.base.status.IStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.ui.activity.utils.WebViewActivity;
import trunk.doi.base.ui.fragment.classify.di.DaggerClassifyComponent;
import trunk.doi.base.ui.fragment.classify.mvp.ClassifyContract;
import trunk.doi.base.ui.fragment.classify.mvp.ClassifyPresenter;

/**
 * Author:
 * Time: 2016/8/12 14:28
 */
public class ClassifyFragment extends LazyLoadFragment<ClassifyPresenter> implements ClassifyContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final String SUB_TYPE = "SUB_TYPE";
    private int mPage = 1;//页数
    private final static int PAGE_COUNT = 10;//每页条数
    private String mSubtype;//分类


    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ClassifyAdapter mClassifyAdapter;

    @Override
    public int initLayoutId() {
        return R.layout.layout_base_refresh_recycler;
    }

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
    public void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {



        mRecyclerView.setLayoutManager(mLayoutManager);
        //刷新控件
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.cff3e19));

        //条目点击
        mClassifyAdapter.setOnMultiItemClickListener((viewHolder, data, position, viewType) ->
                mContext.startActivity(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", data.getDesc())
                        .putExtra("url", data.getUrl()))
        );

        mClassifyAdapter.setOnReloadListener(() -> {
            mClassifyAdapter.setEmptyView(IStatus.STATUS_LOADING);
            loadData();
        });

        mClassifyAdapter.setOnLoadMoreListener(isReload -> loadData());

        assert getArguments() != null;
        mSubtype = getArguments().getString(SUB_TYPE);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setAdapter(mClassifyAdapter);
        mClassifyAdapter.setEmptyView(IStatus.STATUS_LOADING);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


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
    public void onSuccess(List<GankItemData> data) {


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
                mClassifyAdapter.setEmptyView(IStatus.STATUS_FAIL);
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
            mClassifyAdapter.setEmptyView(IStatus.STATUS_FAIL);
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


}

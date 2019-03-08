package trunk.doi.base.ui.activity;

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
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.base.adapter.rvadapter.ViewHolder;
import trunk.doi.base.base.adapter.rvadapter.interfaces.OnItemClickListener;
import trunk.doi.base.bean.CollectionBean;
import trunk.doi.base.gen.DatabaseService;
import trunk.doi.base.ui.activity.utils.WebViewActivity;
import trunk.doi.base.ui.adapter.CollectionAdapter;
import trunk.doi.base.util.WrapContentLinearLayoutManager;
import trunk.doi.base.view.TitleView;


/**
 * 收藏界面
 */
public class CollectionActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TitleView tvTitle;
    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条
    private CollectionAdapter mAdapter;//适配器


    private DatabaseService service;
    private View mLoadingView;
    private View mLoadEmpty;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


        mSwipeRefreshLayout.setColorSchemeResources(R.color.cff3e19);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));

        LinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new CollectionAdapter(mContext, new ArrayList<CollectionBean>(), true);
        mAdapter.setLoadingView(R.layout.view_loading);
        mAdapter.setLoadFailedView(R.layout.view_error);
        mAdapter.setLoadEndView(R.layout.view_nom);

        mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.layout_loading, (ViewGroup) mRecyclerView.getParent(), false);
        mLoadEmpty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_data, (ViewGroup) mRecyclerView.getParent(), false);


    }

    @Override
    public void setListener() {
        tvTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<CollectionBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, CollectionBean gankItemData, int position) {

                startActivity(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", gankItemData.getDesc())
                        .putExtra("url", gankItemData.getUrl()));

            }
        });

        mAdapter.setOnLoadMoreListener(isReload -> loadData());
        mAdapter.setOnDeleteClickListener((viewHolder, data, position) -> {


        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> loadData());

    }

    @Override
    public void initData() {
        mRecyclerView.setAdapter(mAdapter);
        service = new DatabaseService(mContext);
        mAdapter.setEmptyView(mLoadingView);
        loadData();
    }

    private void loadData() {
        mAdapter.removeEmptyView();
        List<CollectionBean> datas = service.queryAll();
        if (datas.size() > 0) {
            mAdapter.reset();
            mAdapter.setNewData(datas);
            mAdapter.loadEnd();
        } else {
            mAdapter.reset();
            mAdapter.setReloadView(mLoadEmpty);
        }

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    protected void onDestroy() {
        service.closeDatabase();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}

package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.lib.base.BaseActivity;
import com.base.lib.view.StatusBarHeight;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.bean.CollectionBean;
import trunk.doi.base.gen.DatabaseService;
import trunk.doi.base.ui.activity.utils.WebViewActivity;
import trunk.doi.base.adapter.CollectionAdapter;
import trunk.doi.base.util.WrapContentLinearLayoutManager;


/**
 * 收藏界面
 */
public class CollectionActivity extends BaseActivity {


    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条
    private CollectionAdapter mAdapter;//适配器


    private DatabaseService service;
    private View mLoadingView;
    private View mLoadEmpty;

    @Override
    protected int initLayoutId(StatusBarHeight statusBar, com.base.lib.view.TitleView titleView) {
        return R.layout.layout_base_refresh_recycler;
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

        mAdapter.setOnItemClickListener((viewHolder, gankItemData, position) -> startActivity(new Intent(mContext, WebViewActivity.class)
                .putExtra("title", gankItemData.getDesc())
                .putExtra("url", gankItemData.getUrl())));

        mAdapter.setOnLoadMoreListener(isReload -> loadData());
        mAdapter.setOnDeleteClickListener((viewHolder, data, position) -> {


        });

        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);

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

package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.base.adapter.rvadapter.ViewHolder;
import trunk.doi.base.base.adapter.rvadapter.interfaces.OnItemClickListener;
import trunk.doi.base.base.adapter.rvadapter.interfaces.OnLoadMoreListener;
import trunk.doi.base.bean.CollectionBean;
import trunk.doi.base.dialog.AlertDialog;
import trunk.doi.base.gen.DatabaseService;
import trunk.doi.base.ui.activity.utils.WebViewActivity;
import trunk.doi.base.ui.adapter.CollectionAdapter;
import trunk.doi.base.view.TitleView;


/**
 * Created by ly on 2016/6/22.
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

    @Override
    protected int initLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mAdapter = new CollectionAdapter(mContext, new ArrayList<CollectionBean>(), true);
        mAdapter.setLoadingView(R.layout.view_loading);
        mAdapter.setLoadFailedView(R.layout.view_error);
        mAdapter.setLoadEndView(R.layout.view_nom);

        mAdapter.setOnItemClickListener(new OnItemClickListener<CollectionBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, CollectionBean gankItemData, int position) {

                startActivityAnim(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", gankItemData.getDesc())
                        .putExtra("url", gankItemData.getUrl()));

            }
        });

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadData();
            }
        });
        mAdapter.setOnDeleteClickListener(new CollectionAdapter.OnDeleteClickListener() {
            @Override
            public void onItemClick(ViewHolder viewHolder,final CollectionBean data, int position) {

                new AlertDialog(mContext).builder().setMsg("是否删除")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确认",new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        service.deleteGankInfo(data.get_id());
                        loadData();
                    }
                }).show();

            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void setListener() {
        tvTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAnim();
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

    }

    @Override
    public void initData() {
        service = new DatabaseService(mContext);
        loadData();
    }

    private void loadData() {

        List<CollectionBean> datas = service.queryAll();
        if (datas.size() > 0) {
                mAdapter.reset();
                mAdapter.setNewData(datas);
                mAdapter.loadEnd();
        } else {
            mAdapter.loadFailed();
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
        finishAnim();
    }

}

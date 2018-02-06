package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.base.adapter.rvadapter.ViewHolder;
import trunk.doi.base.base.adapter.rvadapter.interfaces.OnItemClickListener;
import trunk.doi.base.base.adapter.rvadapter.interfaces.OnLoadMoreListener;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.ui.activity.utils.WebViewActivity;
import trunk.doi.base.ui.adapter.GankItemAdapter;
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

    private int PAGE_COUNT = 1;//页数
    private int mTempPageCount = 2;
    private GankItemAdapter mGankItemAdapter;//适配器
    private boolean isLoadMore;//是否还有

    @Override
    protected int initLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mGankItemAdapter = new GankItemAdapter(mContext, new ArrayList<GankItemData>(), true);
        mGankItemAdapter.setLoadingView(R.layout.view_loading);
        mGankItemAdapter.setLoadFailedView(R.layout.view_error);
        mGankItemAdapter.setLoadEndView(R.layout.view_nom);

        mGankItemAdapter.setOnItemClickListener(new OnItemClickListener<GankItemData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, GankItemData gankItemData, int position) {
                startActivityAnim(new Intent(mContext, WebViewActivity.class)
                        .putExtra("url", gankItemData.getUrl()));
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
                loadData();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mGankItemAdapter);


    }

    @Override
    public void setListener() {
        tvTitle.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAnim();
            }
        });

    }

    @Override
    public void initData() {


    }

    private void loadData(){


    }


    @Override
    public void onBackPressed() {
        finishAnim();
    }


}

package trunk.doi.base.ui.fragment.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.HttpResult;
import trunk.doi.base.https.api.GankItemService;
import trunk.doi.base.https.net.NetManager;
import trunk.doi.base.https.rx.RxManager;
import trunk.doi.base.https.rx.RxSubscriber;
import trunk.doi.base.ui.adapter.GankItemAdapter;
import trunk.doi.base.base.adapter.rvadapter.ViewHolder;
import trunk.doi.base.base.adapter.rvadapter.interfaces.OnItemClickListener;
import trunk.doi.base.base.adapter.rvadapter.interfaces.OnLoadMoreListener;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.ui.activity.utils.WebViewActivity;

/**
 * Author: Othershe
 * Time: 2016/8/12 14:28
 */
public class GankItemFragment extends BaseFragment {

    private static final String SUB_TYPE="SUB_TYPE";
    private int mPage = 1;//页数
    private final static int PAGE_COUNT = 10;//每页条数
    private String mSubtype;//分类
    private GankItemAdapter mGankItemAdapter;//适配器

    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;//进度条

    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        initFetchData();
    }

   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
       mIsViewInitiated = true;
       initFetchData();
   }

    private void initFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            loadData();
            mIsDataInitiated = true;
        }
    }

    @Override
    protected int initLayoutId() {
        return R.layout.layout_base_refresh_recycler;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mGankItemAdapter = new GankItemAdapter(mContext, new ArrayList<GankItemData>(), true);
        mGankItemAdapter.setLoadingView(R.layout.view_loading);
        mGankItemAdapter.setLoadFailedView(R.layout.view_error);
        mGankItemAdapter.setLoadEndView(R.layout.view_nom);

        mGankItemAdapter.setOnItemClickListener(new OnItemClickListener<GankItemData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, GankItemData gankItemData, int position) {
                startActivityAnim(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", gankItemData.getDesc())
                        .putExtra("url", gankItemData.getUrl()));
            }
        });

        mGankItemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadData();
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
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.cff3e19));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                loadData();
            }
        });
        //实现首次自动显示加载提示

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

    }


    public static GankItemFragment newInstance(String subtype) {
        GankItemFragment fragment = new GankItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }

   private void loadData(){

       RxManager.getInstance().doSubscribe(NetManager.getInstance().create(GankItemService.class).getGankItemData("data/" + mSubtype + "/"+PAGE_COUNT+"/" + mPage),
               new RxSubscriber<HttpResult<List<GankItemData>>>(mContext,false,true) {
                   @Override
                   protected void _onNext(HttpResult<List<GankItemData>> listHttpResult) {

                       if(mSwipeRefreshLayout.isRefreshing()){
                           mSwipeRefreshLayout.setRefreshing(false);
                       }
                       if(listHttpResult!=null&&listHttpResult.getResults()!=null&&listHttpResult.getResults().size()>0){

                           if (mPage==1) {
                               mGankItemAdapter.reset();
                               mGankItemAdapter.setNewData(listHttpResult.getResults());
                           } else {
                               mGankItemAdapter.setLoadMoreData(listHttpResult.getResults());
                           }
                           if (listHttpResult.getResults().size() < PAGE_COUNT) {//如果小于10个
                               mGankItemAdapter.loadEnd();
                           }
                           mPage++;
                       }else{
                          mGankItemAdapter.loadFailed();
                       }

                   }
                   @Override
                   protected void _onError(int code) {
                       if(mSwipeRefreshLayout.isRefreshing()){
                           mSwipeRefreshLayout.setRefreshing(false);
                       }
                       mGankItemAdapter.setLoadFailedView(R.layout.view_error);
                   }
               });

   }


}

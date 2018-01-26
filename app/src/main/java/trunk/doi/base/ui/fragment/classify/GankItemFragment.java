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
    private int PAGE_COUNT = 1;//页数
    private String mSubtype;//分类
    private int mTempPageCount = 2;
    private GankItemAdapter mGankItemAdapter;//适配器
    private boolean isLoadMore;//是否还有

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
        return R.layout.fragment_type_item_layout;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mGankItemAdapter = new GankItemAdapter(mContext, new ArrayList<GankItemData>(), true);
        mGankItemAdapter.setLoadingView(R.layout.view_loading);
        mGankItemAdapter.setOnItemClickListener(new OnItemClickListener<GankItemData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, GankItemData gankItemData, int position) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
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
                isLoadMore = false;
                PAGE_COUNT = 1;
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

       RxManager.getInstance().doSubscribe(NetManager.getInstance().create(GankItemService.class).getGankItemData("data/" + mSubtype + "/18/" + PAGE_COUNT),
               new RxSubscriber<HttpResult<List<GankItemData>>>(mContext,false,true) {
                   @Override
                   protected void _onNext(HttpResult<List<GankItemData>> listHttpResult) {

                       if(listHttpResult!=null&&listHttpResult.getResults()!=null&&listHttpResult.getResults().size()>0){
                           if (isLoadMore) {
                               if (listHttpResult.getResults().size() == 0) {
                                   mGankItemAdapter.setLoadEndView(R.layout.view_nom);
                               } else {
                                   mGankItemAdapter.setLoadMoreData(listHttpResult.getResults());
                                   mTempPageCount++;
                               }
                           } else {
                               mGankItemAdapter.setNewData(listHttpResult.getResults());
                               mSwipeRefreshLayout.setRefreshing(false);
                           }
                       }else{
                           if (isLoadMore) {
                               mGankItemAdapter.setLoadFailedView(R.layout.view_error);
                           } else {
                               mSwipeRefreshLayout.setRefreshing(false);
                           }
                       }

                   }

                   @Override
                   protected void _onError(int code) {

                       if (isLoadMore) {
                           mGankItemAdapter.setLoadFailedView(R.layout.view_error);
                       } else {
                           mSwipeRefreshLayout.setRefreshing(false);
                       }

                   }
               });




   }


}

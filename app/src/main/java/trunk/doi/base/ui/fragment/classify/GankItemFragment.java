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
import trunk.doi.base.util.ToastUtil;
import trunk.doi.base.util.WrapContentLinearLayoutManager;

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
    @BindView(R.id.view_net_error)
    View view_net_error;
    @BindView(R.id.view_load)
    View view_load;


    protected boolean mIsViewInitiated;
    protected boolean mIsVisibleToUser;
    protected boolean mIsDataInitiated;

    private View.OnClickListener retryListener;
    private View  mLoadingView;
    private View  mLoadEmpty;

    private boolean isFirdtLoad;


    @Override
    protected int initLayoutId() {
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
        mGankItemAdapter = new GankItemAdapter(mContext, new ArrayList<GankItemData>(), true);
        //RecyclerView 基础布局
        mLoadingView=LayoutInflater.from(mContext).inflate(R.layout.layout_loading,(ViewGroup)mRecyclerView.getParent(),false);
        mLoadEmpty=LayoutInflater.from(mContext).inflate(R.layout.layout_empty_data,(ViewGroup)mRecyclerView.getParent(),false);
        //RecyclerView  footerView  布局
        mGankItemAdapter.setLoadingView(R.layout.view_loading);
        mGankItemAdapter.setLoadFailedView(R.layout.view_error);
        mGankItemAdapter.setLoadEndView(R.layout.view_nom);

    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {

        //加载失败布局
        retryListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPage=1;
                if(view_net_error.getVisibility()==View.VISIBLE){
                    view_net_error.setVisibility(View.GONE);
                }
                view_load.setVisibility(View.VISIBLE);
                loadData();
//                ToastUtil.show(mContext,"云想衣裳花想容,春风拂槛露华浓.\n" + "若非群玉山头见,会向瑶台月下逢.");
            }
        };
        //条目点击
        mGankItemAdapter.setOnItemClickListener(new OnItemClickListener<GankItemData>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, GankItemData gankItemData, int position) {
                startActivityAnim(new Intent(mContext, WebViewActivity.class)
                        .putExtra("title", gankItemData.getDesc())
                        .putExtra("url", gankItemData.getUrl()));
            }
        });
        //加载更多
        mGankItemAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                 loadData();
            }
        });
        //刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                loadData();
            }
        });
        view_net_error.findViewById(R.id.tv_retry).setOnClickListener(retryListener);
        mLoadEmpty.findViewById(R.id.tv_click).setOnClickListener(retryListener);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() == null) {
            return;
        }
        mSwipeRefreshLayout.setEnabled(false);
        mSubtype = getArguments().getString(SUB_TYPE);
        mRecyclerView.setAdapter(mGankItemAdapter);
        //RecyclerView  首次Loading布局
        mGankItemAdapter.setEmptyView(mLoadingView);
    }




   private void loadData(){

       RxManager.getInstance().doSubscribe(NetManager.getInstance().create(GankItemService.class).getGankItemData("data/" + mSubtype + "/"+PAGE_COUNT+"/" + mPage),
               new RxSubscriber<HttpResult<List<GankItemData>>>(mContext,false,true) {
                   @Override
                   protected void _onNext(HttpResult<List<GankItemData>> listHttpResult) {

                       if(mSwipeRefreshLayout.isRefreshing()){
                           mSwipeRefreshLayout.setRefreshing(false);
                       }
                       if(view_load.getVisibility()==View.VISIBLE){
                           view_load.setVisibility(View.GONE);
                       }
                       if(isFirdtLoad){
                           mGankItemAdapter.removeEmptyView();
                           isFirdtLoad=false;
                       }
                       mSwipeRefreshLayout.setEnabled(true);

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
                           if(mPage>1){
                               mGankItemAdapter.loadFailed();
                           }else{
                               mGankItemAdapter.reset();
                               mGankItemAdapter.setReloadView(mLoadEmpty);
                               mSwipeRefreshLayout.setEnabled(false);
                           }
                       }

                   }
                   @Override
                   protected void _onError(int code) {
                       mSwipeRefreshLayout.setEnabled(true);
                       if(mSwipeRefreshLayout.isRefreshing()){
                           mSwipeRefreshLayout.setRefreshing(false);
                       }
                       if(view_load.getVisibility()==View.VISIBLE){
                           view_load.setVisibility(View.GONE);
                       }
                       if(isFirdtLoad){
                           mGankItemAdapter.removeEmptyView();
                           isFirdtLoad=false;
                       }
                       if(mPage>1){
                           mGankItemAdapter.loadFailed();
                       }else{
                           mGankItemAdapter.reset();
                           view_net_error.setVisibility(View.VISIBLE);
                           mSwipeRefreshLayout.setEnabled(false);
                       }
                   }
               });

   }

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

    //ViewPager懒加载方式
    private void initFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            loadData();
            mIsDataInitiated = true;
        }
    }

    public static GankItemFragment newInstance(String subtype) {
        GankItemFragment fragment = new GankItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SUB_TYPE, subtype);
        fragment.setArguments(arguments);
        return fragment;
    }


}

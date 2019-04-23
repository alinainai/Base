package com.base.lib.adapter.rvadapter.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.base.lib.adapter.rvadapter.Util;
import com.base.lib.adapter.rvadapter.ViewHolder;
import com.base.lib.adapter.rvadapter.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 带有加载更多的adapter
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_COMMON_VIEW = 100001;//普通类型 Item
    private static final int TYPE_FOOTER_VIEW = 100002;//footer类型 Item

    private OnLoadMoreListener mLoadMoreListener;

    protected Context mContext;
    private List<T> mDatas;
    private boolean mOpenLoadMore;//是否开启加载更多
    private boolean isAutoLoadMore = true;//是否自动加载，当数据不满一屏幕会自动加载


    private View mLoadingView; //分页加载中view
    private View mLoadFailedView; //分页加载失败view
    private View mLoadEndView; //分页加载结束view

    private RelativeLayout mFooterLayout;//footer view


    private boolean isLoading;//是否正在加载更多

    protected abstract int getViewType(int position, T data);

    public BaseAdapter(Context context, List<T> datas, boolean isOpenLoadMore) {
        mContext = context;
        mDatas = datas == null ? new ArrayList<T>() : datas;
        mOpenLoadMore = isOpenLoadMore;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = new RelativeLayout(mContext);
                }
                viewHolder = ViewHolder.create(mFooterLayout);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        if (mDatas.isEmpty()) {
            return 1;
        }
        return mDatas.size() + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {

        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }

        return getViewType(position, mDatas.get(position));
    }

    /**
     * 根据positiond得到data
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (mDatas.isEmpty()) {
            return null;
        }
        return mDatas.get(position);
    }


    /**
     * StaggeredGridLayoutManager模式时，HeaderView、FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {

        int position = holder.getLayoutPosition();
        if (isFooterView(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， HeaderView、FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }


    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!mOpenLoadMore || mLoadMoreListener == null) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isAutoLoadMore && Util.findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isAutoLoadMore && Util.findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                    if (mDatas.isEmpty()) {
                        return;
                    }
                    scrollLoadMore();
                } else if (isAutoLoadMore) {
                    isAutoLoadMore = false;
                }
            }
        });
    }

    /**
     * 到达底部开始刷新
     */
    private void scrollLoadMore() {


        if (mFooterLayout.getChildAt(0) == mLoadingView && !isLoading) {
            if (mLoadMoreListener != null) {
                isLoading = true;
                mLoadMoreListener.onLoadMore(false);
            }
        }
    }


    /**
     * 根据传入的条目位置判断该条目是否是FooterView类型
     *
     * @param position 条目位置
     * @return true 是FooterView类型
     */
    private boolean isFooterView(int position) {
        return mOpenLoadMore && position >= getItemCount() - 1;
    }

    /**
     * 是否是普通数据条目
     *
     * @param viewType viewType
     * @return true 是数据条目
     */
    protected boolean isCommonItemView(int viewType) {
        return viewType != TYPE_FOOTER_VIEW;
    }


    /**
     * 清空footer view
     */
    private void removeFooterView() {
        mFooterLayout.removeAllViews();
    }

    /**
     * 添加新的footer view
     *
     * @param footerView
     */
    private void addFooterView(View footerView) {
        if (footerView == null) {
            return;
        }

        if (mFooterLayout == null) {
            mFooterLayout = new RelativeLayout(mContext);
        }
        removeFooterView();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterLayout.addView(footerView, params);
    }

    /**
     * 获得列表数据个数
     *
     * @return 列表数据个数
     */
    public int getDataCount() {
        return mDatas.size();
    }

    protected List<T> getAllData() {
        return mDatas;
    }

    /**
     * 刷新加载更多的数据
     *
     * @param datas
     */
    public void setLoadMoreData(List<T> datas) {
        isLoading = false;
        int size = mDatas.size();
        mDatas.addAll(datas);
        notifyItemInserted(size);
    }

    /**
     * 下拉刷新，得到的新数据插入到原数据头部
     *
     * @param datas
     */
    public void setData(List<T> datas) {
        mDatas.addAll(0, datas);
        notifyDataSetChanged();
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     *
     * @param datas
     */
    public void setNewData(List<T> datas) {

        isLoading = false;
        mDatas.clear();
        mDatas.addAll(datas);
        if (mLoadingView != null) {
            addFooterView(mLoadingView);
        }
        isAutoLoadMore = true;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 初始化加载中布局
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
        addFooterView(mLoadingView);
    }

    public void setLoadingView(int loadingId) {
        setLoadingView(Util.inflate(mContext, loadingId));
    }

    /**
     * 初始加载失败布局
     *
     * @param loadFailedView
     */
    public void setLoadFailedView(View loadFailedView) {
        mLoadFailedView = loadFailedView;
    }

    public void setLoadFailedView(int loadFailedId) {
        setLoadFailedView(Util.inflate(mContext, loadFailedId));
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndView
     */
    public void setLoadEndView(View loadEndView) {
        mLoadEndView = loadEndView;
    }

    public void setLoadEndView(int loadEndId) {
        setLoadEndView(Util.inflate(mContext, loadEndId));
    }


    /**
     * 返回 footer view数量
     *
     * @return
     */
    public int getFooterViewCount() {
        return mOpenLoadMore && !mDatas.isEmpty() ? 1 : 0;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }


    /**
     * 数据加载完成
     */
    public void loadEnd() {
        if (mLoadEndView != null) {
            addFooterView(mLoadEndView);
        }
    }

    /**
     * 数据加载失败
     */
    public void loadFailed() {
        addFooterView(mLoadFailedView);
        mLoadFailedView.setOnClickListener(view -> {
            addFooterView(mLoadingView);
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadMore(true);
            }
        });
    }
}

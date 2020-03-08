package com.base.paginate.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.base.paginate.Utils;
import com.base.paginate.interfaces.EmptyInterface;
import com.base.paginate.interfaces.FooterInterface;
import com.base.paginate.interfaces.OnLoadMoreListener;
import com.base.paginate.interfaces.OnReloadListener;
import com.base.paginate.view.DefaultEmptyView;
import com.base.paginate.view.DefaultLoadMoreFooter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * ================================================
 * desc: 加载更多和空布局的adapter
 * <p>
 * created by author ljx
 * Date  2020-03-06
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final String TAG = this.getClass().getSimpleName();

    /**
     * footer 类型的 viewType
     */
    private static final int TYPE_FOOTER_VIEW = 0x00000333;//footer类型 Item

    /**
     * header 类型的 viewType
     */
    private static final int TYPE_BASE_HEADER_VIEW = 0x00000444;

    /**
     * 初始空布局的 viewType
     */
    private static final int TYPE_EMPTY_VIEW = 0x00000555;

    /**
     * 加载更多监听
     */
    private OnLoadMoreListener mLoadMoreListener;

    /**
     * 真正的数据集合
     */
    protected List<T> mData;

    /**
     * 是否开启加载更多功能
     */
    private boolean mOpenLoadMore;

    /**
     * 当数据不满一屏幕会自动加载，是否自动加载
     */
    private boolean isAutoLoadMore = true;

    /**
     * 是否显示初始布局
     */
    private boolean mOpenEmpty;


    /**
     * 初始加载布局的抽象类
     */
    private EmptyInterface mEmptyView;

    /**
     * HeaderLayout的容器
     */
    private LinearLayout mHeaderLayout;

    /**
     * LoadMoreFooter的抽象类，可以展示
     */
    private FooterInterface mFooterLayout;


    /**
     * 是否正在加载更多
     */
    private boolean isLoading;

    /**
     * 失败后重新加载的监听
     */
    private OnReloadListener mReloadListener;


    /**
     * 返回 ViewType
     *
     * @param position
     * @param data
     * @return
     */
    protected abstract int getViewType(int position, T data);

    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);

    protected abstract void convert(RecyclerView.ViewHolder holder, T data, int position, int viewType);


    public BaseAdapter(boolean isOpenLoadMore, boolean openEmpty) {
        init(isOpenLoadMore, openEmpty);
    }

    private void init(boolean isOpenLoadMore, boolean openEmpty) {
        mData = new ArrayList<>();
        mOpenEmpty = openEmpty;
        mOpenLoadMore = isOpenLoadMore;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                mFooterLayout = getFooterLayout(parent.getContext());
                viewHolder = Utils.createPageViewHolder((View) mFooterLayout);
                break;
            case TYPE_BASE_HEADER_VIEW:

                viewHolder = Utils.createPageViewHolder(mHeaderLayout);
                break;
            case TYPE_EMPTY_VIEW:
                mEmptyView = getEmptyLayout(parent.getContext());
                viewHolder = Utils.createPageViewHolder((View) mEmptyView);
                break;
            default:
                viewHolder = getViewHolder(parent, viewType);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, getDataPosition(position), viewType);
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position, final int viewType) {
        convert(holder, mData.get(position), position, viewType);
    }

    @Override
    public int getItemCount() {
        int count;
        if (1 == getEmptyViewCount()) {
            count = 1;
            if (getHeaderCount() != 0) {
                count++;
            }
        } else {
            count = getHeaderCount() + mData.size() + getFooterViewCount();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            if (isHeaderView(position)) {
                return TYPE_BASE_HEADER_VIEW;
            }
            return TYPE_EMPTY_VIEW;
        } else {
            if (isHeaderView(position)) {
                return TYPE_BASE_HEADER_VIEW;
            } else if (isFooterView(position)) {
                return TYPE_FOOTER_VIEW;
            }
            int dataPosition = getDataPosition(position);
            return getViewType(dataPosition, mData.get(dataPosition));
        }
    }


    /**
     * StaggeredGridLayoutManager模式时，HeaderView、FooterView可占据一行
     *
     * @param holder [@link RecyclerView.ViewHolder]
     */
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {

        int position = holder.getLayoutPosition();
        if (isFooterView(position) || isHeaderView(position)) {
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
     * @param recyclerView {@link RecyclerView}
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFooterView(position) || isHeaderView(position)) {
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
     * @param recyclerView  RecyclerView
     * @param layoutManager layoutManager
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

                    //开启加载更过 && 最后一个条目可见 && recyclerView没有在顶端（解决SwipeRefresh下拉刷新后先进行加载更多的bug）
                    if (!isAutoLoadMore && Utils.findLastVisibleItemPosition(layoutManager) + 1 == getItemCount() &&
                            recyclerView.canScrollVertically(-1) && getEmptyViewCount() != 1) {
                        loadMore(false);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isAutoLoadMore && Utils.findLastVisibleItemPosition(layoutManager) + 1 == getItemCount() && getEmptyViewCount() != 1) {
                    if (!mData.isEmpty()) {
                        loadMore(false);
                    }
                } else if (isAutoLoadMore) {
                    isAutoLoadMore = false;
                }
            }
        });
    }

    /**
     * 到达底部开始刷新
     */
    private void loadMore(boolean isReload) {

        if (mFooterLayout.getLoadMoreStatus() != FooterInterface.STATUS_END && !isLoading) {
            if (mLoadMoreListener != null) {
                if (mFooterLayout.getLoadMoreStatus() != FooterInterface.STATUS_LOADING) {
                    mFooterLayout.setStatus(FooterInterface.STATUS_LOADING);
                }
                isLoading = true;
                mLoadMoreListener.onLoadMore(isReload);
            }
        }
    }


    /**
     * 获得列表数据个数
     *
     * @return 列表数据个数
     */

    public int getDataCount() {
        return mData.size();
    }

    /**
     * 把传入的条目position转化为mData的position
     *
     * @param position 条目position
     * @return mData的position
     */
    private int getDataPosition(int position) {
        return position - getHeaderCount();
    }


    public int getEmptyViewCount() {

        if (!mOpenEmpty)
            return 0;

        if (mData.size() != 0) {
            return 0;
        }
        return 1;
    }

    public void setRefreshState(boolean refresh) {
        isLoading = refresh;
    }


    private boolean isHeaderView(int position) {
        return position < getHeaderCount();
    }

    private int getHeaderPosition() {
        return 0;
    }

    protected int getHeaderCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 添加HeaderViewd到headerView后位置
     *
     * @param view View
     */
    @SuppressWarnings("UnusedReturnValue")
    public int addHeaderView(View view) {
        return addHeaderView(view, -1);
    }

    /**
     * @param header {@link View}
     * @param index  the position of header
     */
    public int addHeaderView(View header, final int index) {

        if (header == null)
            return -1;
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
            mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
        final int childCount = mHeaderLayout.getChildCount();
        int mIndex = index;
        if (index < 0 || index >= childCount) {
            mIndex = childCount;
            mHeaderLayout.addView(header, mIndex);
        } else {
            mHeaderLayout.removeViewAt(mIndex);
            mHeaderLayout.addView(header, mIndex);
        }
        if (mHeaderLayout.getChildCount() == 1) {
            notifyItemInserted(getHeaderPosition());
        }
        return mIndex;
    }



    /**
     * remove header view from mHeaderLayout.
     * When the child count of mHeaderLayout is 0, mHeaderLayout will be set to null.
     *
     * @param header 移除HeaderVie中对应的header
     */
    public void removeHeaderView(View header) {
        if (getHeaderCount() == 0) return;

        mHeaderLayout.removeView(header);
        if (mHeaderLayout.getChildCount() == 0) {
            int position = getHeaderPosition();
            if (position != -1) {
                notifyItemRemoved(position);
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
     * 返回 footer view数量
     *
     * @return FooterViewCount
     */
    private int getFooterViewCount() {
        return mOpenLoadMore && !mData.isEmpty() ? 1 : 0;
    }

    /**
     * 是否是普通数据条目
     * @param viewType viewType
     * @return true 是数据条目
     */
    private boolean isCommonItemView(int viewType) {
        return viewType != TYPE_FOOTER_VIEW && viewType != TYPE_BASE_HEADER_VIEW && viewType != TYPE_EMPTY_VIEW;
    }


    /**
     * 刷新加载更多的数据
     *
     * @param data {@link List}
     */
    public void setLoadMoreData(@NonNull List<T> data) {
        if (isLoading)
            isLoading = false;

        if (data.isEmpty()) {
            if (mOpenLoadMore)
                resetFootLoadFail();
            return;
        }

        int size = mData.size();
        mData.addAll(data);
        notifyItemInserted(size + getHeaderCount());
        if (mOpenLoadMore)
            resetLoading();
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     * 会清除掉所有旧数据
     *
     * @param data List<T>
     */
    public void setNewData(List<T> data) {
        isAutoLoadMore = true;
        dataClear();
        if (null != data && !data.isEmpty()) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
        if (mOpenLoadMore)
            resetLoading();
        if (isLoading)
            isLoading = false;
    }

    /**
     * 清空所有数据
     */
    private void dataClear() {
        if (!mData.isEmpty()) {
            mData.clear();
        }
    }


    /**
     * 删除某个位置的数据
     *
     * @param position 数据的位置
     */
    public void remove(int position) {
        if (position >= mData.size() || position < 0) {
            return;
        }
        mData.remove(position);
        int internalPosition = position + getHeaderCount();
        notifyItemRemoved(internalPosition);
        if (mData.isEmpty()) {
            if (mOpenLoadMore)
                resetLoading();
            if (mEmptyView != null) {
                mEmptyView.setStatus(EmptyInterface.STATUS_EMPTY);
            }
            notifyDataSetChanged();
        } else
            notifyItemRangeChanged(internalPosition, mData.size() - position);
    }


    /**
     * 删除某个位置开始的count个数据
     *
     * @param position 数据的位置
     * @param count    数据的个数
     */
    public void removeRange(int position, int count) {
        if (position >= mData.size() || position < 0) {
            return;
        }

        if (position + count > mData.size()) {
            return;
        }
        mData.subList(position, position + count).clear();
        int internalPosition = position + getHeaderCount();
        notifyItemRangeRemoved(internalPosition, count);
        if (mData.isEmpty()) {
            if (mOpenLoadMore)
                resetLoading();
            if (mEmptyView != null) {
                mEmptyView.setStatus(EmptyInterface.STATUS_EMPTY);
            }
            notifyDataSetChanged();
        } else
            notifyItemRangeChanged(internalPosition, mData.size() - position);
    }

    /**
     * 获取item 在mData中的位置
     *
     * @param item 条目
     * @return position
     */
    public int getItemPositionInData(T item) {
        return mData.indexOf(item);
    }

    /**
     * 获取item 在Adapter中的位置
     *
     * @param item 条目
     * @return position
     */
    public int getItemPositionInAdapter(T item) {
        return mData.indexOf(item) + getHeaderCount();
    }

    /**
     * 获取item 在Adapter中的位置
     *
     * @param position 条目在mData的位置
     * @return position
     */
    public int getItemTruePosition(int position) {
        return position + getHeaderCount();
    }

    /**
     * 给列表末尾追加单个数据
     *
     * @param datum 单个数据
     */
    public void insert(T datum) {
        insert(datum, mData.size());
    }

    /**
     * 添加单个数据到指定位置
     *
     * @param datum    data
     * @param position position
     */
    public void insert(T datum, int position) {
        insertAll(Collections.singletonList(datum), position);
    }


    /**
     * 给列表末尾追加多个数据
     *
     * @param data {#{@link List}}多个数据
     */
    public void insertAll(List<T> data) {
        insertAll(data, mData.size());
    }

    /**
     * 从某个位置开始添加若干个数据
     *
     * @param data     多个数据
     * @param position //mData的位置
     */
    public void insertAll(List<T> data, int position) {
        if (position > mData.size() || position < 0) {
            return;
        }
        if (mData.isEmpty()) {
            mData.addAll(0, data);
            notifyDataSetChanged();
        } else {
            mData.addAll(position, data);
            int dataPosition = position + getHeaderCount();
            notifyItemRangeInserted(dataPosition, data.size());
            notifyItemRangeChanged(dataPosition, mData.size() - position);
        }

    }


    public void setEmptyView(@EmptyInterface.EmptyType int statusType) {

        if (!mOpenEmpty || mEmptyView == null)
            return;
        mEmptyView.setStatus(statusType);
        if (statusType == EmptyInterface.STATUS_FAIL || statusType == EmptyInterface.STATUS_NETWORK_FAIL) {

            if (mEmptyView.getRefreshView() != null) {
                mEmptyView.getRefreshView().setOnClickListener(v -> {
                    if (null != mReloadListener) {
                        mReloadListener.onReload();
                    }
                });
            }

        }
        if (mData.size() > 0) {
            int count = mData.size();
            mData.clear();
            notifyDataSetChanged();
        }

    }

    public void setDefaultEmptyView(@NonNull EmptyInterface emptyView) {
        if (!mOpenEmpty)
            return;
        this.mEmptyView = emptyView;
    }

    public void setDefaultFooterLoadMore(@NonNull FooterInterface loadMore) {
        if (!mOpenLoadMore)
            return;
        this.mFooterLayout = loadMore;
    }

    /**
     * 重置foorterView为normal状态
     */
    private void resetLoading() {
        if (mFooterLayout != null) {
            mFooterLayout.setStatus(FooterInterface.STATUS_PRE_LOADING);
        }
    }

    /**
     * 重置foorterView为normal状态
     */
    private void resetFootLoadFail() {
        if (mFooterLayout != null) {
            mFooterLayout.setStatus(FooterInterface.STATUS_FAIL);
            if (mFooterLayout instanceof View) {
                ((View) mFooterLayout).setOnClickListener(v -> loadMore(true));
            }
        }
    }

    /**
     * 数据加载完成
     */
    public void loadEnd() {
        isLoading = false;
        if (mFooterLayout != null) {
            mFooterLayout.setStatus(FooterInterface.STATUS_END);
        }
    }

    /**
     * 数据加载失败
     */
    public void showFooterFail() {
        resetFootLoadFail();
        isLoading = false;
    }


    protected FooterInterface getFooterLayout(Context context) {
        return new DefaultLoadMoreFooter(context);
    }

    protected EmptyInterface getEmptyLayout(Context context) {
        return new DefaultEmptyView(context);
    }


    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public void setOnReloadListener(OnReloadListener reloadListener) {
        mReloadListener = reloadListener;

    }

}
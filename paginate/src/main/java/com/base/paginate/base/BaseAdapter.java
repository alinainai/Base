package com.base.paginate.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.base.paginate.PageViewHolder;
import com.base.paginate.R;
import com.base.paginate.Util;
import com.base.paginate.interfaces.OnLoadMoreListener;
import com.base.paginate.interfaces.OnMultiItemClickListeners;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * 带有加载更多的adapter
 */
@SuppressWarnings("unused")
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final String TAG = this.getClass().getSimpleName();

    private static final int TYPE_FOOTER_VIEW = 100002;//footer类型 Item
    private static final int TYPE_BASE_HEADER_VIEW = 200000;


    private OnLoadMoreListener mLoadMoreListener;
    private OnMultiItemClickListeners<T> mItemClickListener;

    private Context mContext;
    private List<T> mData;
    private boolean mOpenLoadMore;//是否开启加载更多
    private boolean isAutoLoadMore = true;//是否自动加载，当数据不满一屏幕会自动加载

    private View mLoadingView; //分页加载中view
    private View mLoadNormalView; //分页加载失败view
    private View mLoadEndView; //分页加载结束view

    private RelativeLayout mFooterLayout;//footer view
    private LinearLayout mHeaderLayout;
    private boolean isLoading;//是否正在加载更多

    protected abstract int getViewType(int position, T data);

    protected abstract PageViewHolder getViewHolder(ViewGroup parent, int viewType);

    protected abstract void convert(PageViewHolder holder, T data, int position, int viewType);

    @SuppressWarnings("WeakerAccess")
    public BaseAdapter(Context context, List<T> data, boolean isOpenLoadMore) {
        mContext = context;
        mData = data == null ? new ArrayList<>() : data;
        mOpenLoadMore = isOpenLoadMore;
        //初始化三个基础布局
        initBaseLayout();
    }

    private void initBaseLayout() {

        setLoadEndView(R.layout.layout_load_end_footer);
        setLoadingView(R.layout.layout_loading_footer);
        setNormalView(R.layout.layout_load_normal_footer);

    }

    /**
     * @param position
     * @return
     */
    private int getDataPosition(int position) {
        if (getHeaderCount() == 0)
            return position;
        return position - getHeaderCount();
    }


    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PageViewHolder viewHolder;
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = new RelativeLayout(mContext);
                }
                viewHolder = PageViewHolder.create(mFooterLayout);
                break;
            case TYPE_BASE_HEADER_VIEW:
                viewHolder = PageViewHolder.create(mHeaderLayout);
                break;
            default:
                viewHolder = getViewHolder(parent, viewType);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + mData.size() + getFooterViewCount();
    }

    @Override
    public int getItemViewType(int position) {

        if (isHeaderView(position)) {
            return TYPE_BASE_HEADER_VIEW;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }
        int dataPosition = position - getHeaderCount();
        return getViewType(dataPosition, mData.get(dataPosition));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position - getHeaderCount(), viewType);
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position, final int viewType) {
        final PageViewHolder viewHolder = (PageViewHolder) holder;
        convert(viewHolder, mData.get(position), position, viewType);

        viewHolder.getConvertView().setOnClickListener(view -> {
            if (mItemClickListener != null && position < mData.size()) {
                mItemClickListener.onItemClick(viewHolder, mData.get(position), position, viewType);
            }
        });

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
                    if (!isAutoLoadMore && Util.findLastVisibleItemPosition(layoutManager) + 1 == getItemCount() &&
                            recyclerView.canScrollVertically(-1)) {
                        loadMore(false);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isAutoLoadMore && Util.findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
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

        if (mFooterLayout.getChildAt(0) != mLoadEndView && !isLoading) {

            if (mLoadMoreListener != null) {

                if (mFooterLayout.getChildAt(0) != mLoadingView) {
                    addFooterView(mLoadingView);
                }

                isLoading = true;
                mLoadMoreListener.onLoadMore(isReload);
            }
        }
    }

    public void setRefreshState() {
        isLoading = true;
    }


    /**
     * 根据position得到data
     *
     * @param position 位置
     * @return datum
     */
    @SuppressWarnings("unused")
    public T getItem(int position) {
        if (mData.isEmpty() || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    private boolean isHeaderView(int position) {
        return position < getHeaderCount();
    }

    private int getHeaderViewPosition() {
        return 0;
    }

    private int getHeaderCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 添加HeaderView
     *
     * @param view View
     */
    public int addHeaderView(View view) {
        return addHeaderView(view, -1);
    }

    /**
     * @param header
     * @param index
     */
    public int addHeaderView(View header, final int index) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
            mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }
        final int childCount = mHeaderLayout.getChildCount();
        int mIndex = index;
        if (index < 0 || index > childCount) {
            mIndex = childCount;
        }
        mHeaderLayout.addView(header, mIndex);
        if (mHeaderLayout.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return mIndex;
    }


    public int setHeaderView(View header) {
        return setHeaderView(header, 0);
    }

    public int setHeaderView(View header, int index) {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() <= index) {
            return addHeaderView(header, index);
        } else {
            mHeaderLayout.removeViewAt(index);
            mHeaderLayout.addView(header, index);
            return index;
        }
    }


    /**
     * remove header view from mHeaderLayout.
     * When the child count of mHeaderLayout is 0, mHeaderLayout will be set to null.
     *
     * @param header
     */
    public void removeHeaderView(View header) {
        if (getHeaderCount() == 0) return;

        mHeaderLayout.removeView(header);
        if (mHeaderLayout.getChildCount() == 0) {
            int position = getHeaderViewPosition();
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
     * 是否是普通数据条目
     *
     * @param viewType viewType
     * @return true 是数据条目
     */
    private boolean isCommonItemView(int viewType) {
        return viewType != TYPE_FOOTER_VIEW && viewType != TYPE_BASE_HEADER_VIEW;
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
     * 刷新加载更多的数据
     *
     * @param data {@link List}
     */
    public void setLoadMoreData(@NonNull List<T> data) {

        if (isLoading)
            isLoading = false;

        if (data.isEmpty())
            return;
        int size = mData.size();
        mData.addAll(data);
        notifyItemInserted(size + getHeaderCount());

        if (mOpenLoadMore)
            resetLoading();
    }

    /**
     * 下拉刷新，得到的新数据插入到原数据头部
     *
     * @param data List<T>
     */
    public void setData(@NonNull List<T> data) {
        if (isLoading)
            isLoading = false;
        if (data.isEmpty())
            return;
        mData.addAll(0, data);
        notifyItemRangeInserted(getHeaderCount(), data.size());
    }

    /**
     * 初次加载、或下拉刷新要替换全部旧数据时刷新数据
     * 会清除掉所有旧数据
     *
     * @param data List<T>
     */
    public void setNewData(List<T> data) {
        if (isLoading)
            isLoading = false;
        if (!mData.isEmpty())
            mData.clear();
        if (null != data && !data.isEmpty())
            mData.addAll(data);
        notifyDataSetChanged();
        if (mOpenLoadMore)
            resetLoading();
        isAutoLoadMore = true;

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
        notifyItemRemoved(position + getHeaderCount());
        if (mData.isEmpty()) {
            notifyDataSetChanged();
            if (mOpenLoadMore)
                resetLoading();
            isAutoLoadMore = true;
        } else
            notifyItemRangeChanged(position + getHeaderCount(), mData.size() - position);
    }

    /**
     * 从某个位置开始添加若干个数据
     *
     * @param data
     * @param position
     */
    public void insert(List<T> data, int position) {
        if (position > mData.size() || position < 0) {
            return;
        }
        mData.addAll(position, data);
        notifyItemRangeInserted(position + getHeaderCount(), data.size());
        notifyItemRangeChanged(position + getHeaderCount(), mData.size() - position);
    }

    /**
     * 给列表末尾追加多个数据
     *
     * @param data
     */
    public void insert(List<T> data) {
        insert(data, mData.size());
    }

    /**
     * 添加单个数据到指定位置
     *
     * @param data     data
     * @param position position
     */
    public void insert(T data, int position) {
        if (position > mData.size() || position < 0) {
            return;
        }
        mData.add(position, data);
        notifyItemInserted(position + getHeaderCount());
        notifyItemRangeChanged(position + getHeaderCount(), mData.size() - position);
    }

    /**
     * 给列表末尾追加单个数据
     *
     * @param data
     */
    public void insert(T data) {
        insert(data, mData.size());
    }


    private void resetLoading() {
        addFooterView(mLoadNormalView);
    }


    /**
     * 数据加载完成
     */
    public void loadEnd() {
        isLoading = false;
        if (mLoadEndView != null) {
            addFooterView(mLoadEndView);
        } else {
            addFooterView(new View(mContext));
        }
    }

    /**
     * 数据加载失败
     */
    public void showNormal() {
        addFooterView(mLoadNormalView);
        isLoading = false;
    }

    private void addFooterView(View view) {
        if (view == null) {
            return;
        }
        if (mFooterLayout == null) {
            mFooterLayout = new RelativeLayout(mContext);
        }
        Util.removeChildView(mFooterLayout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFooterLayout.addView(view, params);
    }


    /**
     * 初始化加载中布局
     *
     * @param loadingView {@link View}
     */
    @SuppressWarnings("WeakerAccess")
    public void setLoadingView(View loadingView) {
        mLoadingView = loadingView;
    }

    @SuppressWarnings("WeakerAccess")
    public void setLoadingView(int loadingId) {
        setLoadingView(Util.inflate(mContext, loadingId));
    }

    /**
     * 初始加载失败布局
     *
     * @param loadFailedView {@link View}
     */
    @SuppressWarnings("WeakerAccess")
    public void setNormalView(View loadFailedView) {
        mLoadNormalView = loadFailedView;
        mLoadNormalView.setOnClickListener(view -> {
            loadMore(true);
        });
        addFooterView(mLoadNormalView);
    }

    @SuppressWarnings("WeakerAccess")
    public void setNormalView(int loadFailedId) {
        setNormalView(Util.inflate(mContext, loadFailedId));
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndView {@link View}
     */
    @SuppressWarnings("WeakerAccess")
    public void setLoadEndView(View loadEndView) {
        mLoadEndView = loadEndView;
    }

    @SuppressWarnings("WeakerAccess")
    public void setLoadEndView(int loadEndId) {
        setLoadEndView(Util.inflate(mContext, loadEndId));
    }




    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public void setOnMultiItemClickListener(OnMultiItemClickListeners<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }


}

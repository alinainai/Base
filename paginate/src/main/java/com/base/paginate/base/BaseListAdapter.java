package com.base.paginate.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.base.paginate.Utils;
import com.base.paginate.interfaces.EmptyInterface;
import com.base.paginate.interfaces.FooterInterface;
import com.base.paginate.interfaces.OnLoadMoreListener;
import com.base.paginate.interfaces.OnReloadListener;
import com.base.paginate.view.DefaultEmptyView;
import com.base.paginate.view.DefaultLoadMoreFooter;

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
public abstract class BaseListAdapter<T> extends ListAdapter<T, RecyclerView.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();

    /**
     * footer 类型的 viewType
     */
    private static final int TYPE_FOOTER_VIEW = 0x00001001;//footer类型 Item

    /**
     * header 类型的 viewType
     */
    private static final int TYPE_BASE_HEADER_VIEW = 0x00001002;

    /**
     * 初始空布局的 viewType
     */
    private static final int TYPE_EMPTY_VIEW = 0x00001003;

    /**
     * 加载更多监听
     */
    private OnLoadMoreListener mLoadMoreListener;


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

    protected abstract void convert(RecyclerView.ViewHolder holder, T data, int position);

    protected abstract void convertDiff(RecyclerView.ViewHolder holder, T data, int position, List<Object> payloads);


    public BaseListAdapter(boolean isOpenLoadMore, boolean openEmpty, DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        init(isOpenLoadMore, openEmpty);
    }

    public BaseListAdapter(boolean isOpenLoadMore, boolean openEmpty, @NonNull AsyncDifferConfig<T> config) {
        super(config);
        init(isOpenLoadMore, openEmpty);
    }

    private void init(boolean isOpenLoadMore, boolean openEmpty) {
        mOpenEmpty = openEmpty;
        mOpenLoadMore = isOpenLoadMore;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                if (mFooterLayout == null) {
                    mFooterLayout = getFooterLayout(parent.getContext());
                }
                viewHolder = Utils.createPageViewHolder((View) mFooterLayout);
                break;
            case TYPE_BASE_HEADER_VIEW:
                viewHolder = Utils.createPageViewHolder(mHeaderLayout);
                break;
            case TYPE_EMPTY_VIEW:
                if (mEmptyView == null) {
                    mEmptyView = getEmptyLayout(parent.getContext());
                }
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
            bindCommonItem(holder, adapterPositionToDataPosition(position));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            int positionOfData = adapterPositionToDataPosition(position);
            convertDiff(holder, getData().get(positionOfData), positionOfData, payloads);
        }

    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        convert(holder, getData().get(position), position);
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
            count = getHeaderCount() + super.getItemCount() + getFooterViewCount();
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
            int dataPosition = adapterPositionToDataPosition(position);
            return getViewType(dataPosition, getData().get(dataPosition));
        }
    }

    private List<T> getData() {
        return getCurrentList();
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

        if (mOpenEmpty) {
            if (mEmptyView == null) {
                mEmptyView = getEmptyLayout(recyclerView.getContext());
            }
        }
        if (mOpenLoadMore) {
            if (mFooterLayout == null) {
                mFooterLayout = getFooterLayout(recyclerView.getContext());
            }
        }

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
                    if (!getData().isEmpty()) {
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

    public void setRefreshState(boolean refresh) {
        isLoading = refresh;
    }


    /**
     * 是否是普通数据条目
     *
     * @param viewType viewType
     * @return true 是数据条目
     */
    private boolean isCommonItemView(int viewType) {
        return viewType != TYPE_FOOTER_VIEW && viewType != TYPE_BASE_HEADER_VIEW && viewType != TYPE_EMPTY_VIEW;
    }


    /********************************** HeaderView ****************************************/

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


    /********************************** EmptyView Method ****************************************/


    public int getEmptyViewCount() {

        if (!mOpenEmpty)
            return 0;

        if (getData().size() != 0) {
            return 0;
        }
        return 1;
    }

    protected EmptyInterface getEmptyLayout(Context context) {
        return new DefaultEmptyView(context);
    }

    public void setEmptyView(@EmptyInterface.EmptyType int statusType) {

        if (!getData().isEmpty()) {
            return;
        }
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


    }

    public void setDefaultEmptyView(@NonNull EmptyInterface emptyView) {
        if (!mOpenEmpty)
            return;
        this.mEmptyView = emptyView;
    }

    /********************************** FooterView Method ****************************************/

    protected FooterInterface getFooterLayout(Context context) {
        return new DefaultLoadMoreFooter(context);
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
        return mOpenLoadMore && !getData().isEmpty() ? 1 : 0;
    }


    public void setDefaultFooterLoadMore(@NonNull FooterInterface loadMore) {
        if (!mOpenLoadMore)
            return;
        this.mFooterLayout = loadMore;
    }

    /**
     * 重置footerView为normal状态
     */
    private void resetLoading() {
        if (mFooterLayout != null) {
            mFooterLayout.setStatus(FooterInterface.STATUS_PRE_LOADING);
        }
    }

    /**
     * 重置footerView为fail状态
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

    /********************************** 数据相关 Method ****************************************/

    /**
     * 获得列表数据个数
     *
     * @return 列表数据个数
     */

    public int getDataSize() {
        return getData().size();
    }

    /**
     * 把传入的条目position转化为mData的position
     *
     * @param position 条目position
     * @return mData的position
     */
    private int adapterPositionToDataPosition(int position) {
        return position - getHeaderCount();
    }

    /**
     * 数据变化调用
     *
     * @param data List<T>
     */
    public void setDataDiff(List<T> data) {

        if (isLoading)
            isLoading = false;
        if (data.isEmpty()||getData().isEmpty()) {
            isAutoLoadMore = true;
        }
        submitList(data);
        if (mOpenLoadMore)
            resetLoading();

    }

    @Override
    public void onCurrentListChanged(@NonNull List<T> previousList, @NonNull List<T> currentList) {
        super.onCurrentListChanged(previousList, currentList);

    }

    /************************************** Set Listener ****************************************/

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    public void setOnReloadListener(OnReloadListener reloadListener) {
        mReloadListener = reloadListener;

    }

}
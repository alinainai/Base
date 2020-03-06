package com.base.paginate;

import android.content.Context;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.base.paginate.viewholder.PageViewHolder;

/**
 * Author: Othershe
 * Time: 2016/9/1 11:57
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Utils {
    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     *
     * @param lastVisiblePositions 可见item的position数组
     * @return 使用StaggeredGridLayoutManager时最大的可见条目的position
     */
    public static int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    /**
     * StaggeredGridLayoutManager时，查找position最小的值
     *
     * @param firstVisiblePositions 可见item的position数组
     * @return 使用StaggeredGridLayoutManager时最小的可见条目的position
     */
    public static int findMin(int[] firstVisiblePositions) {
        int min = firstVisiblePositions[0];
        for (int value : firstVisiblePositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static View inflate(Context context, int layoutId) {
        if (layoutId <= 0)
            throw new IllegalArgumentException("layoutId error");
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    /**
     * 返回传入的{@link RecyclerView.LayoutManager}对象最后一个可见的 position
     *
     * @param layoutManager RecyclerView.LayoutManager
     * @return 最后一个可见条目的position
     */
    public static int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    /**
     * 返回传入的{@link RecyclerView.LayoutManager}对象最后一个可见的 position
     *
     * @param layoutManager RecyclerView.LayoutManager
     * @return 最后一个可见条目的position
     */
    public static int findFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
            return findMin(lastVisibleItemPositions);
        }
        return -1;
    }


    public static void removeChildView(ViewGroup vp) {
        if (null != vp && vp.getChildCount() > 0)
            vp.removeAllViews();
    }



    public static int dpToPx(Context context,float dp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, context.getApplicationContext().getResources().getDisplayMetrics());
    }


    /**
     * 生成 PageViewHolder 对象
     *
     * @param layoutId
     * @param parent
     * @return
     */
    public static PageViewHolder createPageViewHolder(int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return createPageViewHolder(itemView);
    }

    /**
     * 生成 PageViewHolder 对象
     *
     * @param itemView
     * @return
     */
    public static PageViewHolder createPageViewHolder(View itemView) {
        return new PageViewHolder(itemView);
    }


}

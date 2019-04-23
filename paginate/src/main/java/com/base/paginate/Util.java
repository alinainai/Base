package com.base.paginate;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Othershe
 * Time: 2016/9/1 11:57
 */
public class Util {
    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     *
     * @param lastVisiblePositions
     * @return
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


    public static void removeChildView(ViewGroup vp) {
        if (null != vp && vp.getChildCount() > 0)
            vp.removeAllViews();
    }


}
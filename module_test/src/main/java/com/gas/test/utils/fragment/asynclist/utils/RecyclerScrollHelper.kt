package com.gas.test.utils.fragment.asynclist.utils

import android.os.SystemClock
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lib.commonsdk.extension.app.debug
import com.lib.commonsdk.extension.app.dpToPx

class RecyclerScrollHelper(private val recycler: RecyclerView, private val topHeight: Int = 0) {

    private var mLastDragTime: Long = 0
    private var mListState = 0

    //处理recyclerView滑动很长距离时要微调一下最后停止的位置所用到的参数
    private var mPendingToScrollPosition = RecyclerView.NO_POSITION

    fun scrollTo(position: Int) {
        if (position >= 0) {
            recycler.apply {
                post {
                    recycler.layoutManager.takeIf { it is LinearLayoutManager }?.let { layoutManager ->
                        val firstItem = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val lastItem = layoutManager.findLastVisibleItemPosition()
                        if (firstItem != RecyclerView.NO_POSITION) {
                            if (position in firstItem..lastItem) {
                                recycler.getChildAt(position - firstItem)?.let { toItemView ->
                                    val topDistance = toItemView.top - topHeight.dpToPx().toInt()
                                    recycler.smoothScrollBy(0, topDistance)
                                }
                            } else {
                                mPendingToScrollPosition = position
                                if (position < firstItem && firstItem - position > 10) {
                                    recycler.scrollToPosition(position + 10)
                                    recycler.post { recycler.smoothScrollToPosition(position) }
                                } else if (position > lastItem && position - lastItem > 10) {
                                    recycler.scrollToPosition(position - 10)
                                    recycler.post { recycler.smoothScrollToPosition(position) }
                                } else {
                                    recycler.smoothScrollToPosition(position)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun isListDragging(): Boolean {
        return mListState == RecyclerView.SCROLL_STATE_DRAGGING || SystemClock.elapsedRealtime() - mLastDragTime < 5 * 1000
    }

    init {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                mListState = newState
                if (mPendingToScrollPosition != RecyclerView.NO_POSITION && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollTo(mPendingToScrollPosition)
                    debug("scrollTo $mPendingToScrollPosition")
                    mPendingToScrollPosition = RecyclerView.NO_POSITION
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mPendingToScrollPosition = RecyclerView.NO_POSITION
                }
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mLastDragTime = SystemClock.elapsedRealtime()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

    }

}
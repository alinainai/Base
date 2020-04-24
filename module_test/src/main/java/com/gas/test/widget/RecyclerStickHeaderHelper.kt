package com.gas.test.widget

import android.util.SparseArray
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * ================================================
 * desc:RecyclerView 实现吸顶的辅助类
 *
 * 使用方法：具体参见示例代码
 * 1.首先在布局中初始化一个 FrameLayout 布局，注意要和 RecyclerView 高度齐平。
 * 2.在 RecyclerView 设置完 Adapter 之后调用 DockTopViewHelper 包装 FrameLayout 和 RecyclerView，
 *   并在第三个参数中传入 要吸顶的 ViewHolder 的 ViewType。
 *
 * created by author ljx
 * Date  2020/4/22
 * email lijiaxing@360.cn
 *
 * ================================================
 */
class RecyclerStickHeaderHelper(private val mRecyclerView: RecyclerView, private val mDockItemViewWrapper: FrameLayout, vararg stickHeadType: Int) {

    companion object {

        //用于在吸顶布局中保存 ViewHolder 的 key。
        const val VIEW_HOLDER_TAG = -101

        //用于在吸顶布局中保存位置的 key。
        const val POSITION_TAG = -102

        //用于在吸顶布局中保存 viewType 的 key。
        const val VIEW_TYPE_TAG = -103
    }

    private var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    //保存吸顶布局的缓存池。它以列表组头的viewType为key,ViewHolder为value对吸顶布局进行保存和回收复用。
    private val mStickyViews = SparseArray<RecyclerView.ViewHolder>()
    private lateinit var mStickTypes: HashSet<Int>
    private val mDataChangeObserver: AdapterDataObserver by lazy {

        object : AdapterDataObserver() {
            override fun onChanged() {
                mRecyclerView.post {
                    checkAndFixDock(true)
                }
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) { // do nothing
                mRecyclerView.post {
                    checkAndFixDock(true)
                }
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) { // do nothing
                mRecyclerView.post {
                    checkAndFixDock(true)
                }
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) { // do nothing
                mRecyclerView.post {
                    checkAndFixDock(true)
                }
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                mRecyclerView.post {
                    checkAndFixDock(false)
                }
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                mRecyclerView.post {
                    checkAndFixDock(false)
                }
            }
        }
    }

    init {
        if (stickHeadType.isNotEmpty()) {
            mDockItemViewWrapper.visibility = View.INVISIBLE
            mDockItemViewWrapper.setTag(POSITION_TAG, RecyclerView.NO_POSITION)
            mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    mRecyclerView.post {
                        checkAndFixDock(false)
                    }
                }
            })
            mStickTypes = HashSet(stickHeadType.size)
            mStickTypes.addAll(stickHeadType.asList())
            mAdapter = mRecyclerView.adapter
            mAdapter?.registerAdapterDataObserver(mDataChangeObserver)

        }

    }

    fun checkAndFixDock(force: Boolean) {
        if (mRecyclerView.adapter?.itemCount!! > 0) {
            val topItemPos = getFirstVisibleItem()
            if (topItemPos < 0 || mRecyclerView.adapter!!.itemCount <= topItemPos) {
                return
            }
            if (force) {
                mDockItemViewWrapper.setTag(POSITION_TAG, RecyclerView.NO_POSITION)
            }

            if (isItemNeedDockTop(topItemPos)) {
                showDockForItem(topItemPos)
            } else {
                // is collapsed item
                // top is collapsed, next is collapsible item, dock to this collapsed item y
                val prevCollapsiblePos = findPreviousNeedDockItemPos(topItemPos)
                if (prevCollapsiblePos >= 0) {
                    showDockForItem(prevCollapsiblePos)
                } else { // view is not ready
                    hideDock()
                }
            }
            mDockItemViewWrapper.translationY = calculateOffset(topItemPos)
        }

    }


    private fun showDockForItem(pos: Int) {

        mAdapter?.apply {
            if (mDockItemViewWrapper.getTag(POSITION_TAG) != pos) {

                mDockItemViewWrapper.setTag(POSITION_TAG, pos)

                val viewType: Int = getItemViewType(pos)

                //如果当前的吸顶布局的类型和我们需要的一样，就直接获取它的ViewHolder，否则就回收。
                var holder = recycleStickyView(viewType)

                //标志holder是否是从当前吸顶布局取出来的。
                val flag = holder != null
                if (holder == null) { //从缓存池中获取吸顶布局。
                    holder = getStickyViewByType(viewType)
                }
                //如果没有从缓存池中获取到吸顶布局，则通过RecyclerViewAdapter创建。
                if (holder == null) {
                    holder = onCreateViewHolder(mDockItemViewWrapper, viewType)
                    holder.itemView.setTag(VIEW_TYPE_TAG, viewType)
                    holder.itemView.setTag(VIEW_HOLDER_TAG, holder)
                }

                //通过RecyclerViewAdapter更新吸顶布局的数据。
                //这样可以保证吸顶布局的显示效果跟列表中的组头保持一致。
                onBindViewHolder(holder, pos)

                //如果holder不是从当前吸顶布局取出来的，就需要把吸顶布局添加到容器里。
                if (!flag) {
                    mDockItemViewWrapper.addView(holder.itemView)
                }

                mDockItemViewWrapper.visibility = View.VISIBLE
                if (mDockItemViewWrapper.childCount > 0 && mDockItemViewWrapper.height == 0) {
                    mDockItemViewWrapper.requestLayout()
                }
            }
        }

    }

    private fun calculateOffset(firstVisibleItem: Int): Float {

        mAdapter?.apply {
            val nextCollapsibleItemPos = findNextNeedDockItemPos(firstVisibleItem)
            if (nextCollapsibleItemPos != -1) {
                val index = nextCollapsibleItemPos - firstVisibleItem
                mRecyclerView.apply {
                    if (childCount > index) {
                        //获取下一个组的组头的itemView。
                        layoutManager?.let {
                            it.findViewByPosition(nextCollapsibleItemPos)?.let { v ->
                                val off = v.y - mDockItemViewWrapper.height
                                if (off < 0) {
                                    return off
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0F

    }

    private fun hideDock() {
        recycle()
        mDockItemViewWrapper.setTag(POSITION_TAG, RecyclerView.NO_POSITION)
        mDockItemViewWrapper.y = 0f
        mDockItemViewWrapper.visibility = View.GONE
    }


    /**
     * 判断是否需要先回收吸顶布局，如果要回收，则回收吸顶布局并返回null。
     * 如果不回收，则返回吸顶布局的ViewHolder。
     * 这样做可以避免频繁的添加和移除吸顶布局。
     *
     * @param viewType
     * @return
     */
    private fun recycleStickyView(viewType: Int): RecyclerView.ViewHolder? {
        if (mDockItemViewWrapper.childCount > 0) {
            val view: View = mDockItemViewWrapper.getChildAt(0)
            val type = view.getTag(VIEW_TYPE_TAG) as Int
            if (type == viewType) {
                return view.getTag(VIEW_HOLDER_TAG) as RecyclerView.ViewHolder
            } else {
                recycle()
            }
        }
        return null
    }

    /**
     * 回收并移除吸顶布局
     */
    private fun recycle() {
        if (mDockItemViewWrapper.childCount > 0) {
            val view: View = mDockItemViewWrapper.getChildAt(0)
            mStickyViews.put(view.getTag(VIEW_TYPE_TAG) as Int,
                    view.getTag(VIEW_HOLDER_TAG) as RecyclerView.ViewHolder)
            mDockItemViewWrapper.removeAllViews()
        }
    }

    /**
     * 从缓存池中获取吸顶布局
     *
     * @param viewType 吸顶布局的viewType
     * @return
     */
    private fun getStickyViewByType(viewType: Int): RecyclerView.ViewHolder? {
        return mStickyViews[viewType]
    }


    /**
     * 获取当前第一个显示的item .
     */
    private fun getFirstVisibleItem(): Int {
        var firstVisibleItem = -1
        val layout = mRecyclerView.layoutManager
        if (layout != null) {
            when (layout) {
                is GridLayoutManager -> {
                    firstVisibleItem = layout.findFirstVisibleItemPosition()
                }

                is LinearLayoutManager -> {
                    firstVisibleItem = layout.findFirstVisibleItemPosition()
                }

                is StaggeredGridLayoutManager -> {
                    val firstPositions = IntArray(layout.spanCount)
                    layout.findFirstVisibleItemPositions(firstPositions)
                    firstVisibleItem = getMin(firstPositions)
                }
            }
        }
        return firstVisibleItem
    }

    private fun getMin(arr: IntArray): Int {
        var min = arr[0]
        for (x in 1 until arr.size) {
            if (arr[x] < min) min = arr[x]
        }
        return min
    }

    /**
     * 通过对比 viewHolder 的 itemViewType，判断当前 position 是否是吸顶
     */
    private fun isItemNeedDockTop(pos: Int): Boolean {
        mAdapter?.apply {
            return mStickTypes.contains(mAdapter!!.getItemViewType(pos))
        }
        return false
    }

    /**
     * 根据 position 找出上一个吸顶 ViewHolder
     */
    private fun findPreviousNeedDockItemPos(pos: Int): Int {
        mAdapter?.apply {
            if (pos < itemCount - 1) {
                for (i in pos - 1 downTo 0) {
                    if (mStickTypes.contains(getItemViewType(i))) {
                        return i
                    }
                }
            }
        }
        return RecyclerView.NO_POSITION
    }

    /**
     * 根据 position 找出下一个吸顶 ViewHolder
     */
    private fun findNextNeedDockItemPos(pos: Int): Int {
        mAdapter?.apply {
            if (pos < itemCount - 1) {
                for (i in pos + 1 until itemCount) {
                    if (mStickTypes.contains(getItemViewType(i))) {
                        return i
                    }
                }
            }
        }
        return RecyclerView.NO_POSITION
    }


}

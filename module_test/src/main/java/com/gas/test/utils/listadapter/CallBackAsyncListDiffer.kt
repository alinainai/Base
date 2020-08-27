package com.gas.test.utils.listadapter

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CallBackAsyncListDiffer<T>(private val mUpdateCallback: ListUpdateCallback,
                                 private val mConfig: CallbackAsyncDifferConfig<T>) {
    private var mMainThreadExecutor: Executor? = null

    private class MainThreadExecutor internal constructor() : Executor {
        val mHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mHandler.post(command)
        }
    }

    private var mList: List<T>? = null
    var currentList: List<T> = emptyList()
        private set
    private var mMaxScheduledGeneration = 0

    /**
     * 通过子线程更新数据然后在主线程的 mMainThreadExecutor 中返回计算结果
     */
    fun submitList(newList: List<T>?, runnable: Runnable) {
        // incrementing generation means any currently-running diffs are discarded when they finish
        val runGeneration = ++mMaxScheduledGeneration
        if (newList === mList) {
            return
        }

        // fast simple remove all
        if (newList == null) {
            val countRemoved = mList!!.size
            mList = null
            currentList = emptyList()
            // notify last, after list is updated
            mUpdateCallback.onRemoved(0, countRemoved)
            runnable.run()
            return
        }

        // fast simple first insert
        if (mList == null) {
            mList = newList
            currentList = Collections.unmodifiableList(newList)
            // notify last, after list is updated
            mUpdateCallback.onInserted(0, newList.size)
            runnable.run()
            return
        }
        val oldList: List<T> = mList!!
        mConfig.backgroundThreadExecutor?.execute {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldList.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem: T? = oldList[oldItemPosition]
                    val newItem: T? = newList[newItemPosition]
                    return if (oldItem != null && newItem != null) {
                        mConfig.diffCallback.areItemsTheSame(oldItem, newItem)
                    } else oldItem == null && newItem == null
                    // If both items are null we consider them the same.
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val oldItem: T? = oldList[oldItemPosition]
                    val newItem: T? = newList[newItemPosition]
                    if (oldItem != null && newItem != null) {
                        return mConfig.diffCallback.areContentsTheSame(oldItem, newItem)
                    }
                    if (oldItem == null && newItem == null) {
                        return true
                    }
                    throw AssertionError()
                }

                override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                    val oldItem: T? = oldList[oldItemPosition]
                    val newItem: T? = newList[newItemPosition]
                    if (oldItem != null && newItem != null) {
                        return mConfig.diffCallback.getChangePayload(oldItem, newItem)
                    }
                    throw AssertionError()
                }
            })
            mMainThreadExecutor!!.execute {
                if (mMaxScheduledGeneration == runGeneration) {
                    latchList(newList, result)
                    runnable.run()
                }
            }
        }
    }

    private fun latchList(newList: List<T>, diffResult: DiffUtil.DiffResult) {
        mList = newList
        currentList = Collections.unmodifiableList(newList)
        diffResult.dispatchUpdatesTo(mUpdateCallback)
    }

    class CallbackAsyncDifferConfig<T> internal constructor(
            val mainThreadExecutor: Executor?,
            val backgroundThreadExecutor: Executor?,
            val diffCallback: DiffUtil.ItemCallback<T>) {


        class Builder<T>(private val mDiffCallback: DiffUtil.ItemCallback<T>) {
            private var mMainThreadExecutor: Executor? = null
            private var mBackgroundThreadExecutor: Executor? = null

            fun build(): CallbackAsyncDifferConfig<T> {
                if (mBackgroundThreadExecutor == null) {
                    synchronized(sExecutorLock) {
                        if (sDiffExecutor == null) {
                            sDiffExecutor = Executors.newFixedThreadPool(2)
                        }
                    }
                    mBackgroundThreadExecutor = sDiffExecutor
                }
                return CallbackAsyncDifferConfig(
                        mMainThreadExecutor,
                        mBackgroundThreadExecutor,
                        mDiffCallback)
            }

            companion object {
                private val sExecutorLock = Any()
                private var sDiffExecutor: Executor? = null
            }

        }

    }

    companion object {
        private val sMainThreadExecutor: Executor = MainThreadExecutor()
    }

    init {
        mMainThreadExecutor = mConfig.mainThreadExecutor ?: sMainThreadExecutor
    }
}
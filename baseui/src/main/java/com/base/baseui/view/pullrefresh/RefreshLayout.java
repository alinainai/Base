package com.base.baseui.view.pullrefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;


/**
 * 作者：Mr.Lee on 2017-8-29 10:17
 * 邮箱：569932357@qq.com
 */

public class RefreshLayout extends ViewGroup {

    private static final String TAG = "RefreshLayout";
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    private static final int START_POSITION = 0;

    // scroller duration
    private static final int SCROLL_TO_TOP_DURATION = 500;
    private static final int SCROLL_TO_REFRESH_DURATION = 250;
    private static final long SHOW_COMPLETED_TIME = 500;
    //刷新头
    private View refreshHeader;
    //主体控件
    private View target;
    // 主体控件初始位置Offset
    private int currentTargetOffsetTop;
    private int lastTargetOffsetTop;
    // 是否已经计算头部高度
    private boolean hasMeasureHeader;
    private int touchSlop;
    // header高度
    private int headerHeight;
    // 需要下拉这个距离才进入松手刷新状态，默认和header高度一致
    private int totalDragDistance;
    private int maxDragDistance;
    private int activePointerId;
    private boolean isTouch;
    private boolean hasSendCancelEvent;
    private float lastMotionX;
    private float lastMotionY;
    private float initDownY;
    private float initDownX;
    private MotionEvent lastEvent;
    private boolean mIsBeginDragged;
    private AutoScroll autoScroll;
    private State state = State.RESET;
    private OnRefreshListener refreshListener;
    private boolean isAutoRefresh;

    // 刷新成功，显示500ms成功状态再滚动回顶部
    private final Runnable delayToScrollTopRunnable = new Runnable() {
        @Override
        public void run() {
            //自动滚动到初始位置
            autoScroll.scrollTo(START_POSITION, SCROLL_TO_TOP_DURATION);
        }
    };

    private final Runnable autoRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            // 标记当前是自动刷新状态，finishScroll调用时需要判断
            // 在actionDown事件中重新标记为false
            isAutoRefresh = true;
            changeState(State.PULL);
            autoScroll.scrollTo(totalDragDistance, SCROLL_TO_REFRESH_DURATION);
        }
    };


    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        autoScroll = new AutoScroll();
        //添加默认刷新头
        setRefreshHeader(new DefaultHeader(context));
    }

    /**
     * 设置自定义header
     */
    public void setRefreshHeader(View view) {
        if (view != null && view != refreshHeader) {
            removeView(refreshHeader);

            // 为header添加默认的layoutParams
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
            }
            refreshHeader = view;
            addView(refreshHeader);
        }
    }

    /**
     * 设置刷新监听器
     *
     * @param refreshListener
     */
    public void setRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    /**
     * 刷新结束
     */
    public void refreshComplete() {
        changeState(State.COMPLETE);
        // if refresh completed and the target at top, change state to reset.
        if (currentTargetOffsetTop == START_POSITION) {
            changeState(State.RESET);
        } else {
            // waiting for a time to showToast refreshView completed state.
            // at next touch event, remove this runnable
            if (!isTouch) {
                postDelayed(delayToScrollTopRunnable, SHOW_COMPLETED_TIME);
            }
        }
    }

    /**
     * 自动刷新，默认动画500ms
     */
    public void autoRefresh() {
        autoRefresh(500);
    }

    /**
     * 在onCreate中调用autoRefresh，此时View可能还没有初始化好，需要延长一段时间执行。
     *
     * @param duration 延时执行的毫秒值
     */
    public void autoRefresh(long duration) {
        if (state != State.RESET) {
            return;
        }
        postDelayed(autoRefreshRunnable, duration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (target == null) {
            ensureTarget();
        }

        if (target == null) {
            return;
        }

        // ----- measure target -----
        // target占满整屏
        target.measure(
                MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                        MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(
                        getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                        MeasureSpec.EXACTLY));

        // ----- measure refreshView-----
        measureChild(refreshHeader, widthMeasureSpec, heightMeasureSpec);
        if (!hasMeasureHeader) { // 防止header重复测量
            hasMeasureHeader = true;
            headerHeight = refreshHeader.getMeasuredHeight(); // header高度
            totalDragDistance = headerHeight;   // 需要pull这个距离才进入松手刷新状态
            if (maxDragDistance == 0) {  // 默认最大下拉距离为控件高度的五分之四
                maxDragDistance = totalDragDistance * 3;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }

        if (target == null) {
            ensureTarget();
        }
        if (target == null) {
            return;
        }

        // target铺满屏幕
        final View child = target;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop() + currentTargetOffsetTop;
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

        // header放到target的上方，水平居中
        int refreshViewWidth = refreshHeader.getMeasuredWidth();
        refreshHeader.layout((width / 2 - refreshViewWidth / 2),
                -headerHeight + currentTargetOffsetTop,
                (width / 2 + refreshViewWidth / 2),
                currentTargetOffsetTop);
    }

    /**
     * 把第一个非refreshHeader的Child作为target
     */
    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laidout yet.
        if (target == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(refreshHeader)) {
                    target = child;
                    break;
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || target == null) {
            return super.dispatchTouchEvent(ev);
        }

        final int actionMasked = ev.getActionMasked(); // support Multi-touch 支持多指触控
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                activePointerId = ev.getPointerId(0);
                isAutoRefresh = false;
                isTouch = true;
                hasSendCancelEvent = false;
                mIsBeginDragged = false;
                lastTargetOffsetTop = currentTargetOffsetTop;
                currentTargetOffsetTop = target.getTop();
                initDownX = lastMotionX = ev.getX(0);
                initDownY = lastMotionY = ev.getY(0);
                autoScroll.stop();
                removeCallbacks(delayToScrollTopRunnable);
                removeCallbacks(autoRefreshRunnable);
                super.dispatchTouchEvent(ev);
                return true;    // return true，否则可能接受不到move和up事件

            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_POINTER) {
                    return super.dispatchTouchEvent(ev);
                }
                lastEvent = ev;
                float x = ev.getX(ev.findPointerIndex(activePointerId));
                float y = ev.getY(ev.findPointerIndex(activePointerId));
                float yDiff = y - lastMotionY;
                float offsetY = yDiff * DRAG_RATE;
                lastMotionX = x;
                lastMotionY = y;

                if (!mIsBeginDragged && Math.abs(y - initDownY) > 50
                        && Math.abs(y - initDownY) > Math.abs(x - initDownX)
                        && Math.abs(y - initDownY) > touchSlop) {
                    mIsBeginDragged = true;
                }

                if (mIsBeginDragged) {
                    boolean moveDown = offsetY > 0; // ↓
                    boolean canMoveDown = canChildScrollUp();
                    boolean moveUp = !moveDown;     // ↑
                    boolean canMoveUp = target.getTop() > START_POSITION;

                    // 判断是否拦截事件
                    if ((moveDown && !canMoveDown) || (moveUp && canMoveUp)) {
                        moveSpinner(offsetY);
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouch = false;
                if (currentTargetOffsetTop > START_POSITION || target.getTop() < START_POSITION) {
                    finishSpinner();
                }
                activePointerId = INVALID_POINTER;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerIndex = ev.getActionIndex();
                if (pointerIndex < 0) {
                    return super.dispatchTouchEvent(ev);
                }
                lastMotionX = ev.getX(pointerIndex);
                lastMotionY = ev.getY(pointerIndex);
                lastEvent = ev;
                activePointerId = ev.getPointerId(pointerIndex);
                break;

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                lastMotionY = ev.getY(ev.findPointerIndex(activePointerId));
                lastMotionX = ev.getX(ev.findPointerIndex(activePointerId));
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    private void moveSpinner(float diff) {
        int offset = Math.round(diff);
        if (offset == 0) {
            return;
        }

        // 发送cancel事件给child
        if (!hasSendCancelEvent && isTouch && currentTargetOffsetTop > START_POSITION) {
            sendCancelEvent();
            hasSendCancelEvent = true;
        }

        int targetY = Math.max(0, currentTargetOffsetTop + offset); // target不能移动到小于0的位置……
        // y = x - (x/2)^2   添加阻尼系数
        float extraOS = targetY - totalDragDistance;
        float slingshotDist = totalDragDistance;
        float tensionSlingshotPercent = Math.max(0, Math.min(extraOS, slingshotDist * 2) / slingshotDist);
        float tensionPercent = (float) (tensionSlingshotPercent - Math.pow(tensionSlingshotPercent / 2, 2));

        if (offset > 0) { // 下拉的时候才添加阻力
            offset = (int) (offset * (1f - tensionPercent));
            targetY = Math.max(0, currentTargetOffsetTop + offset);
        }

        // 1. 在RESET状态时，第一次下拉出现header的时候，设置状态变成PULL
        if (state == State.RESET && currentTargetOffsetTop == START_POSITION && targetY > 0) {
            changeState(State.PULL);
        }

        // 2. 在PULL或者COMPLETE状态时，header回到顶部的时候，状态变回RESET
        if (currentTargetOffsetTop > START_POSITION && targetY <= START_POSITION) {
            if (state == State.PULL || state == State.COMPLETE) {
                changeState(State.RESET);
            }
        }

        // 3. 如果是从底部回到顶部的过程(往上滚动)，并且手指是松开状态, 并且当前是PULL状态，状态变成LOADING，这时候我们需要强制停止autoScroll
        if (state == State.PULL && !isTouch && currentTargetOffsetTop > totalDragDistance && targetY <= totalDragDistance) {
            autoScroll.stop();
            changeState(State.LOADING);
            if (refreshListener != null) {
                refreshListener.onRefresh();
            }
            // 因为判断条件targetY <= totalDragDistance，会导致不能回到正确的刷新高度（有那么一丁点偏差），调整change
            int adjustOffset = totalDragDistance - targetY;
            offset += adjustOffset;
        }

        setTargetOffsetTopAndBottom(offset);

        // 别忘了回调header的位置改变方法。
        if (refreshHeader instanceof RefreshHeader) {
            ((RefreshHeader) refreshHeader)
                    .onPositionChange(currentTargetOffsetTop, lastTargetOffsetTop, totalDragDistance, isTouch, state);
        }

    }

    private void finishSpinner() {
        if (state == State.LOADING) {//如果是刷新状态，滚动到header高度刚好显示
            if (currentTargetOffsetTop > totalDragDistance) {
                autoScroll.scrollTo(totalDragDistance, SCROLL_TO_REFRESH_DURATION);
            }
        } else {
            //滚动0
            autoScroll.scrollTo(START_POSITION, SCROLL_TO_TOP_DURATION);
        }
    }


    private void changeState(State state) {
        this.state = state;
        RefreshHeader refreshHeader = this.refreshHeader instanceof RefreshHeader ? ((RefreshHeader) this.refreshHeader) : null;
        if (refreshHeader != null) {
            switch (state) {
                case RESET:
                    refreshHeader.reset();
                    break;
                case PULL:
                    refreshHeader.pull();
                    break;
                case LOADING:
                    refreshHeader.refreshing();
                    break;
                case COMPLETE:
                    refreshHeader.complete();
                    break;
            }
        }
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        if (offset == 0) {
            return;
        }
        target.offsetTopAndBottom(offset);
        refreshHeader.offsetTopAndBottom(offset);
        lastTargetOffsetTop = currentTargetOffsetTop;
        currentTargetOffsetTop = target.getTop();
        invalidate();
    }

    private void sendCancelEvent() {
        if (lastEvent == null) {
            return;
        }
        MotionEvent ev = MotionEvent.obtain(lastEvent);
        ev.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(ev);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = ev.getActionIndex();
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == activePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            lastMotionY = ev.getY(newPointerIndex);
            lastMotionX = ev.getX(newPointerIndex);
            activePointerId = ev.getPointerId(newPointerIndex);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (target instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) target;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return target.canScrollVertically(-1) || target.getScrollY() > 0;
            }
        } else {
            return target.canScrollVertically(-1);
        }
    }


    private class AutoScroll implements Runnable {
        private Scroller scroller;
        private int lastY;

        public AutoScroll() {
            scroller = new Scroller(getContext());
        }

        @Override
        public void run() {
            boolean finished = !scroller.computeScrollOffset() || scroller.isFinished();
            if (!finished) {
                int currY = scroller.getCurrY();
                int offset = currY - lastY;
                lastY = currY;
                moveSpinner(offset);
                post(this);
                onScrollFinish(false);
            } else {
                stop();
                onScrollFinish(true);
            }
        }

        public void scrollTo(int to, int duration) {
            int from = currentTargetOffsetTop;
            int distance = to - from;
            stop();
            if (distance == 0) {
                return;
            }
            scroller.startScroll(0, 0, 0, distance, duration);
            post(this);
        }

        private void stop() {
            removeCallbacks(this);
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
            lastY = 0;
        }
    }

    /**
     * 在scroll结束的时候会回调这个方法
     *
     * @param isForceFinish 是否是强制结束的
     */
    private void onScrollFinish(boolean isForceFinish) {
        if (isAutoRefresh && !isForceFinish) {
            isAutoRefresh = false;
            changeState(State.LOADING);
            if (refreshListener != null) {
                refreshListener.onRefresh();
            }
            finishSpinner();
        }
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

}

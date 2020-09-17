package com.gas.test.utils.view.tablayoutkt;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gas.test.R;
import com.gas.test.utils.view.tablayoutkt.adapter.QMUITabAdapter;
import com.gas.test.utils.view.tablayoutkt.callback.TabBuilderUpdater;
import com.gas.test.utils.view.tablayoutkt.indicator.QMUITabIndicator;
import com.gas.test.utils.view.tablayoutkt.tab.QMUITab;
import com.gas.test.utils.view.tablayoutkt.tab.QMUITabView;
import com.gas.test.utils.view.tablayoutkt.utils.TabUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


public class QMUIBasicTabSegment extends HorizontalScrollView {

    private static final String TAG = "QMUIBasicTabSegment";
    public static final int MODE_SCROLLABLE = 0;
    public static final int MODE_FIXED = 1;
    public static final int NO_POSITION = -1;
    private final ArrayList<OnTabSelectedListener> mSelectedListeners = new ArrayList<>();
    private Container mContentLayout;
    protected int mCurrentSelectedIndex = NO_POSITION;
    protected int mPendingSelectedIndex = NO_POSITION;
    private QMUITabIndicator mIndicator = null;
    private boolean mHideIndicatorWhenTabCountLessTwo = true;

    @Mode
    private int mMode = MODE_FIXED;
    private int mItemSpaceInScrollMode;
    private QMUITabAdapter mTabAdapter;
    protected QMUITabBuilder mTabBuilder;
    private boolean mSelectNoAnimation;
    protected Animator mSelectAnimator;
    private OnTabClickListener mOnTabClickListener;
    private boolean mIsInSelectTab = false;


    public QMUIBasicTabSegment(Context context) {
        this(context, null);
    }

    public QMUIBasicTabSegment(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.QMUITabSegmentStyle);
    }

    public QMUIBasicTabSegment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        setClipToPadding(false);
        setClipChildren(false);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.QMUITabSegment, defStyleAttr, 0);

        // indicator
        boolean hasIndicator = array.getBoolean(R.styleable.QMUITabSegment_qmui_tab_has_indicator, false);
        int indicatorHeight = array.getDimensionPixelSize(R.styleable.QMUITabSegment_qmui_tab_indicator_height,
                getResources().getDimensionPixelSize(R.dimen.qmui_tab_segment_indicator_height));
        boolean indicatorTop = array.getBoolean(R.styleable.QMUITabSegment_qmui_tab_indicator_top, false);
        boolean indicatorWidthFollowContent = array.getBoolean(R.styleable.QMUITabSegment_qmui_tab_indicator_with_follow_content, false);
        mIndicator = createTabIndicatorFromXmlInfo(hasIndicator, indicatorHeight, indicatorTop, indicatorWidthFollowContent);
        // tabBuilder
        int normalTextSize = array.getDimensionPixelSize(R.styleable.QMUITabSegment_android_textSize,
                getResources().getDimensionPixelSize(R.dimen.qmui_tab_segment_text_size));
        normalTextSize = array.getDimensionPixelSize(R.styleable.QMUITabSegment_qmui_tab_normal_text_size, normalTextSize);
        int selectedTextSize = normalTextSize;
        selectedTextSize = array.getDimensionPixelSize(R.styleable.QMUITabSegment_qmui_tab_selected_text_size, selectedTextSize);
        mTabBuilder = new QMUITabBuilder(context).setTextSize(normalTextSize, selectedTextSize);
        mMode = array.getInt(R.styleable.QMUITabSegment_qmui_tab_mode, MODE_FIXED);
        mItemSpaceInScrollMode = array.getDimensionPixelSize(R.styleable.QMUITabSegment_qmui_tab_space, TabUtils.dpToPx(context, 10));
        mSelectNoAnimation = array.getBoolean(R.styleable.QMUITabSegment_qmui_tab_select_no_animation, false);
        array.recycle();
        mContentLayout = new Container(context);
        addView(mContentLayout, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mTabAdapter = createTabAdapter(mContentLayout);
    }

    public void setDefaultTextSize(int normalTextSize, int selectedTextSize) {
        mTabBuilder.setTextSize(normalTextSize, selectedTextSize);
    }

    public void updateParentTabBuilder(TabBuilderUpdater updater) {
        updater.update(mTabBuilder);
    }

    protected QMUITabAdapter createTabAdapter(ViewGroup tabParentView) {
        return new QMUITabAdapter(this, tabParentView);
    }

    protected QMUITabIndicator createTabIndicatorFromXmlInfo(boolean hasIndicator, int indicatorHeight, boolean indicatorTop, boolean indicatorWidthFollowContent) {
        if (!hasIndicator) {
            return null;
        }
        return new QMUITabIndicator(indicatorHeight, indicatorTop, indicatorWidthFollowContent);
    }

    public QMUITabBuilder tabBuilder() {
        // do not change mTabBuilder to keep common config not changed
        return new QMUITabBuilder(mTabBuilder);
    }

    /**
     * replace with custom indicator
     *
     * @param indicator if null, present there is not a indicator
     */
    public void setIndicator(@Nullable QMUITabIndicator indicator) {
        mIndicator = indicator;
        mContentLayout.requestLayout();
    }

    public void setHideIndicatorWhenTabCountLessTwo(boolean hideIndicatorWhenTabCountLessTwo) {
        mHideIndicatorWhenTabCountLessTwo = hideIndicatorWhenTabCountLessTwo;
    }

    public void setItemSpaceInScrollMode(int itemSpaceInScrollMode) {
        mItemSpaceInScrollMode = itemSpaceInScrollMode;
    }

    /**
     * clear all tabs
     */
    public void reset() {
        mTabAdapter.clear();
        mCurrentSelectedIndex = NO_POSITION;
        if (mSelectAnimator != null) {
            mSelectAnimator.cancel();
            mSelectAnimator = null;
        }
    }

    /**
     * clear select info
     */
    public void resetSelect() {
        mCurrentSelectedIndex = NO_POSITION;
        if (mSelectAnimator != null) {
            mSelectAnimator.cancel();
            mSelectAnimator = null;
        }
    }


    /**
     * add a tab to QMUITabSegment
     *
     * @param tab QMUITab
     * @return return this to chain
     */
    public QMUIBasicTabSegment addTab(QMUITab tab) {
        mTabAdapter.addItem(tab);
        return this;
    }


    /**
     * notify dataChanged event to QMUITabSegment
     */
    public void notifyDataChanged() {
        int current = mCurrentSelectedIndex;
        resetSelect();
        mTabAdapter.setup();
        selectTab(current);
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener listener) {
        if (!mSelectedListeners.contains(listener)) {
            mSelectedListeners.add(listener);
        }
    }

    public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener listener) {
        mSelectedListeners.remove(listener);
    }

    public void clearOnTabSelectedListeners() {
        mSelectedListeners.clear();
    }

    public int getMode() {
        return mMode;
    }

    public void setMode(@Mode int mode) {
        if (mMode != mode) {
            mMode = mode;
            if (mode == MODE_SCROLLABLE) {
                mTabBuilder.setGravity(Gravity.LEFT);
            }
            mContentLayout.invalidate();
        }
    }


    public void onClickTab(QMUITabView view, int index) {
        if (mSelectAnimator != null || needPreventEvent()) {
            return;
        }

        if (mOnTabClickListener != null) {
            if (mOnTabClickListener.onTabClick(view, index)) {
                return;
            }
        }

        QMUITab model = mTabAdapter.getItem(index);
        if (model != null) {
            selectTab(index, mSelectNoAnimation, true);
        }
    }

    protected boolean needPreventEvent() {
        return false;
    }

    public void onDoubleClick(int index) {
        if (mSelectedListeners.isEmpty()) {
            return;
        }
        QMUITab model = mTabAdapter.getItem(index);
        if (model != null) {
            dispatchTabDoubleTap(index);
        }
    }

    private void dispatchTabSelected(int index) {
        for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
            mSelectedListeners.get(i).onTabSelected(index);
        }
    }

    private void dispatchTabUnselected(int index) {
        for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
            mSelectedListeners.get(i).onTabUnselected(index);
        }
    }

    private void dispatchTabReselected(int index) {
        for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
            mSelectedListeners.get(i).onTabReselected(index);
        }
    }

    private void dispatchTabDoubleTap(int index) {
        for (int i = mSelectedListeners.size() - 1; i >= 0; i--) {
            mSelectedListeners.get(i).onDoubleTap(index);
        }
    }

    public void setSelectNoAnimation(boolean noAnimation) {
        mSelectNoAnimation = noAnimation;
    }

    public void selectTab(int index) {
        selectTab(index, mSelectNoAnimation, false);
    }

    public void selectTab(final int index, boolean noAnimation, boolean fromTabClick) {
        if (mIsInSelectTab) {
            return;
        }
        mIsInSelectTab = true;
        List<QMUITabView> listViews = mTabAdapter.getViews();
        if (listViews.size() != mTabAdapter.getSize()) {
            mTabAdapter.setup();
            listViews = mTabAdapter.getViews();
        }
        if (listViews.size() == 0 || listViews.size() <= index) {
            mIsInSelectTab = false;
            return;
        }
        if (mSelectAnimator != null || needPreventEvent()) {
            mPendingSelectedIndex = index;
            mIsInSelectTab = false;
            return;
        }

        if (mCurrentSelectedIndex == index) {
            if (fromTabClick) {
                // dispatch re select only when click tab
                dispatchTabReselected(index);
            }
            mIsInSelectTab = false;
            // invalidate mContentLayout to sure indicator is drawn if needed
            mContentLayout.invalidate();
            return;
        }

        if (mCurrentSelectedIndex > listViews.size()) {
            Log.i(TAG, "selectTab: current selected index is bigger than views size.");
            mCurrentSelectedIndex = NO_POSITION;
        }
        // first time to select
        if (mCurrentSelectedIndex == NO_POSITION) {
            QMUITab model = mTabAdapter.getItem(index);
            layoutIndicator(model, true);
            QMUITabView tabView = listViews.get(index);
            tabView.setSelected(true); // 标记选中，使得TalkBack等屏幕阅读器可向用户报告tab状态
            tabView.setSelectFraction(1f);
            dispatchTabSelected(index);
            mCurrentSelectedIndex = index;
            mIsInSelectTab = false;
            return;
        }

        final int prev = mCurrentSelectedIndex;
        final QMUITab prevModel = mTabAdapter.getItem(prev);
        final QMUITabView prevView = listViews.get(prev);
        final QMUITab nowModel = mTabAdapter.getItem(index);
        final QMUITabView nowView = listViews.get(index);

        if (noAnimation) {
            dispatchTabUnselected(prev);
            dispatchTabSelected(index);
            prevView.setSelectFraction(0f);
            prevView.setSelected(false); // 标记未选中，使得TalkBack等屏幕阅读器可向用户报告tab状态
            nowView.setSelectFraction(1f);
            nowView.setSelected(true); // 标记选中，使得TalkBack等屏幕阅读器可向用户报告tab状态
            if (mMode == MODE_SCROLLABLE) {
                int scrollX = getScrollX(),
                        w = getWidth(),
                        cw = mContentLayout.getWidth(),
                        nl = nowView.getLeft(),
                        nw = nowView.getWidth();
                int paddingHor = getPaddingLeft() + getPaddingRight();
                int size = mTabAdapter.getSize();
                int maxScrollX = cw - w + paddingHor;
                if (index > prev) {
                    if (index >= size - 2) {
                        smoothScrollBy(maxScrollX - scrollX, 0);
                    } else {
                        int nextWidth = listViews.get(index + 1).getWidth();
                        int targetScrollX = Math.min(maxScrollX, nl - (w - getPaddingRight() * 2 - nextWidth - nw - mItemSpaceInScrollMode));
                        targetScrollX -= nextWidth - nw;
                        if (scrollX < targetScrollX) {
                            smoothScrollBy(targetScrollX - scrollX, 0);
                        }
                    }
                } else {
                    if (index <= 1) {
                        smoothScrollBy(-scrollX, 0);
                    } else {
                        int prevWidth = listViews.get(index - 1).getWidth();
                        int targetScrollX = Math.max(0, nl - prevWidth - mItemSpaceInScrollMode);
                        if (targetScrollX < scrollX) {
                            smoothScrollBy(targetScrollX - scrollX, 0);
                        }
                    }
                }
            }
            mCurrentSelectedIndex = index;
            mIsInSelectTab = false;
            layoutIndicator(nowModel, true);
            return;
        }

        final ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animValue = (float) animation.getAnimatedValue();
                prevView.setSelectFraction(1 - animValue);
                nowView.setSelectFraction(animValue);
                layoutIndicatorInTransition(prevModel, nowModel, animValue);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mSelectAnimator = animation;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                prevView.setSelectFraction(0f);
                prevView.setSelected(false); // 标记未选中，使得TalkBack等屏幕阅读器可向用户报告tab状态
                nowView.setSelectFraction(1f);
                nowView.setSelected(true); // 标记选中，使得TalkBack等屏幕阅读器可向用户报告tab状态
                mSelectAnimator = null;
                // set current selected index first, dispatchTabSelected may call selectTab again.
                mCurrentSelectedIndex = index;
                dispatchTabSelected(index);
                dispatchTabUnselected(prev);
                if (mPendingSelectedIndex != NO_POSITION && !needPreventEvent()) {
                    selectTab(mPendingSelectedIndex, true, false);
                    mPendingSelectedIndex = NO_POSITION;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mSelectAnimator = null;
                prevView.setSelectFraction(1f);
                prevView.setSelected(true); // 标记选中，使得TalkBack等屏幕阅读器可向用户报告tab状态
                nowView.setSelectFraction(0f);
                nowView.setSelected(false); // 标记未选中，使得TalkBack等屏幕阅读器可向用户报告tab状态
                layoutIndicator(prevModel, true);

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(200);
        animator.start();
        mIsInSelectTab = false;
    }

    private void layoutIndicator(QMUITab model, boolean invalidate) {
        if (model == null || mIndicator == null) {
            return;
        }
        mIndicator.updateInfo(model.contentLeft, model.contentWidth, model.selectedColorAttr == 0 ? model.selectColor : model.selectedColorAttr, 0f);
        if (invalidate) {
            mContentLayout.invalidate();
        }
    }

    private void layoutIndicatorInTransition(QMUITab preModel, QMUITab targetModel, float offsetPercent) {
        if (mIndicator == null) {
            return;
        }
        final int leftDistance = targetModel.contentLeft - preModel.contentLeft;
        final int widthDistance = targetModel.contentWidth - preModel.contentWidth;
        final int targetLeft = (int) (preModel.contentLeft + leftDistance * offsetPercent);
        final int targetWidth = (int) (preModel.contentWidth + widthDistance * offsetPercent);
        int indicatorColor = TabUtils.computeColor(
                preModel.selectedColorAttr == 0 ? preModel.selectColor : preModel.selectedColorAttr,
                targetModel.selectedColorAttr == 0 ? targetModel.selectColor : targetModel.selectedColorAttr,
                offsetPercent);
        mIndicator.updateInfo(targetLeft, targetWidth, indicatorColor, offsetPercent);
        mContentLayout.invalidate();
    }

    public void updateIndicatorPosition(final int index, float offsetPercent) {
        if (mSelectAnimator != null || mIsInSelectTab || offsetPercent == 0) {
            return;
        }

        int targetIndex;
        if (offsetPercent < 0) {
            targetIndex = index - 1;
            offsetPercent = -offsetPercent;
        } else {
            targetIndex = index + 1;
        }

        final List<QMUITabView> listViews = mTabAdapter.getViews();
        if (listViews.size() <= index || listViews.size() <= targetIndex) {
            return;
        }
        QMUITab preModel = mTabAdapter.getItem(index);
        QMUITab targetModel = mTabAdapter.getItem(targetIndex);
        QMUITabView preView = listViews.get(index);
        QMUITabView targetView = listViews.get(targetIndex);
        preView.setSelectFraction(1 - offsetPercent);
        targetView.setSelectFraction(offsetPercent);
        layoutIndicatorInTransition(preModel, targetModel, offsetPercent);
    }

    /**
     * 改变 Tab 的文案
     *
     * @param index Tab 的 index
     * @param text  新文案
     */
    public void updateTabText(int index, String text) {
        QMUITab model = mTabAdapter.getItem(index);
        if (model == null) {
            return;
        }
        model.setText(text);
        notifyDataChanged();
    }

    /**
     * 整个 Tab 替换
     *
     * @param index 需要被替换的 Tab 的 index
     * @param model 新的 Tab
     */
    public void replaceTab(int index, QMUITab model) {
        try {
            if (mCurrentSelectedIndex == index) {
                // re select
                mCurrentSelectedIndex = NO_POSITION;
            }
            mTabAdapter.replaceItem(index, model);
            notifyDataChanged();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (getChildCount() > 0) {
            final View child = getChildAt(0);
            int paddingHor = getPaddingLeft() + getPaddingRight();
            child.measure(MeasureSpec.makeMeasureSpec(widthSize - paddingHor, MeasureSpec.EXACTLY), heightMeasureSpec);
            if (widthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(Math.min(widthSize, child.getMeasuredWidth() + paddingHor), heightMeasureSpec);
                return;
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    public int getSelectedIndex() {
        return mCurrentSelectedIndex;
    }

    public int getTabCount() {
        return mTabAdapter.getSize();
    }

    /**
     * get {@link QMUITab} by index
     *
     * @param index index
     * @return QMUITab
     */
    public QMUITab getTab(int index) {
        return mTabAdapter.getItem(index);
    }


    /**
     * show signCount/redPoint by index
     *
     * @param index the index of tab
     * @param count if count > 0, show signCount; else if count == 0 show redPoint; else show nothing
     */
    public void showSignCountView(Context context, int index, int count) {
        QMUITab tab = mTabAdapter.getItem(index);
        tab.setSignCount(count);
        notifyDataChanged();
    }

    /**
     * clear signCount/redPoint by index
     *
     * @param index the index of tab
     */
    public void clearSignCountView(int index) {
        QMUITab tab = mTabAdapter.getItem(index);
        tab.clearSignCountOrRedPoint();
        notifyDataChanged();
    }

    /**
     * get sign count by index
     *
     * @param index the index of tab
     */
    public int getSignCount(int index) {
        QMUITab tab = mTabAdapter.getItem(index);
        return tab.getSignCount();
    }

    /**
     * is redPoint showing ?
     *
     * @param index the index of tab
     * @return true if redPoint is showing
     */
    public boolean isRedPointShowing(int index) {
        return mTabAdapter.getItem(index).isRedPointShowing();
    }

    @IntDef(value = {MODE_SCROLLABLE, MODE_FIXED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }


    public interface OnTabClickListener {
        /**
         * 当某个 Tab 被点击时会触发
         *
         * @param tabView 被点击的View
         * @param index   被点击的 Tab 下标
         * @return true 拦截 selectTab 事件
         */
        boolean onTabClick(QMUITabView tabView, int index);
    }

    public interface OnTabSelectedListener {
        /**
         * 当某个 Tab 被选中时会触发
         *
         * @param index 被选中的 Tab 下标
         */
        void onTabSelected(int index);

        /**
         * 当某个 Tab 被取消选中时会触发
         *
         * @param index 被取消选中的 Tab 下标
         */
        void onTabUnselected(int index);

        /**
         * 当某个 Tab 处于被选中状态下再次被点击时会触发
         *
         * @param index 被再次点击的 Tab 下标
         */
        void onTabReselected(int index);

        /**
         * 当某个 Tab 被双击时会触发
         *
         * @param index 被双击的 Tab 下标
         */
        void onDoubleTap(int index);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mCurrentSelectedIndex != NO_POSITION && mMode == MODE_SCROLLABLE) {
            final QMUITabView view = mTabAdapter.getViews().get(mCurrentSelectedIndex);
            if (getScrollX() > view.getLeft()) {
                scrollTo(view.getLeft(), 0);
            } else {
                int realWidth = getWidth() - getPaddingRight() - getPaddingLeft();
                if (getScrollX() + realWidth < view.getRight()) {
                    scrollBy(view.getRight() - realWidth - getScrollX(), 0);
                }
            }
        }
    }


    private final class Container extends ViewGroup {

        public Container(Context context) {
            super(context);
            setClipChildren(false);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
            List<QMUITabView> childViews = mTabAdapter.getViews();
            int size = childViews.size();
            int i;

            int visibleChild = 0;
            for (i = 0; i < size; i++) {
                View child = childViews.get(i);
                if (child.getVisibility() == VISIBLE) {
                    visibleChild++;
                }
            }
            if (size == 0 || visibleChild == 0) {
                setMeasuredDimension(widthSpecSize, heightSpecSize);
                return;
            }

            int childHeight = heightSpecSize - getPaddingTop() - getPaddingBottom();
            int childWidthMeasureSpec, childHeightMeasureSpec, resultWidthSize = 0;
            if (mMode == MODE_FIXED) {
                resultWidthSize = widthSpecSize;
                int modeFixItemWidth = widthSpecSize / visibleChild;
                for (i = 0; i < size; i++) {
                    final View child = childViews.get(i);
                    if (child.getVisibility() != VISIBLE) {
                        continue;
                    }
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(modeFixItemWidth, MeasureSpec.EXACTLY);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                    // reset
                    QMUITab tab = mTabAdapter.getItem(i);
                    tab.leftAddonMargin = 0;
                    tab.rightAddonMargin = 0;
                }
            } else {
                float totalWeight = 0;
                for (i = 0; i < size; i++) {
                    final View child = childViews.get(i);
                    if (child.getVisibility() != VISIBLE) {
                        continue;
                    }
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpecSize, MeasureSpec.AT_MOST);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                    resultWidthSize += child.getMeasuredWidth() + mItemSpaceInScrollMode;

                    QMUITab tab = mTabAdapter.getItem(i);
                    totalWeight += tab.leftSpaceWeight + tab.rightSpaceWeight;

                    // reset first
                    tab.leftAddonMargin = 0;
                    tab.rightAddonMargin = 0;
                }

                resultWidthSize -= mItemSpaceInScrollMode;

                if (totalWeight > 0 && resultWidthSize < widthSpecSize) {
                    int remain = widthSpecSize - resultWidthSize;
                    resultWidthSize = widthSpecSize;
                    for (i = 0; i < size; i++) {
                        final View child = childViews.get(i);
                        if (child.getVisibility() != VISIBLE) {
                            continue;
                        }
                        QMUITab tab = mTabAdapter.getItem(i);
                        tab.leftAddonMargin = (int) (remain * tab.leftSpaceWeight / totalWeight);
                        tab.rightAddonMargin = (int) (remain * tab.rightSpaceWeight / totalWeight);
                    }
                }
            }

            setMeasuredDimension(resultWidthSize, heightSpecSize);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            List<QMUITabView> childViews = mTabAdapter.getViews();
            int size = childViews.size();
            int i;
            int visibleChild = 0;
            for (i = 0; i < size; i++) {
                View child = childViews.get(i);
                if (child.getVisibility() == VISIBLE) {
                    visibleChild++;
                }
            }

            if (size == 0 || visibleChild == 0) {
                return;
            }

            int usedLeft = getPaddingLeft();
            for (i = 0; i < size; i++) {
                QMUITabView childView = childViews.get(i);
                if (childView.getVisibility() != VISIBLE) {
                    continue;
                }
                final int childMeasureWidth = childView.getMeasuredWidth();
                QMUITab model = mTabAdapter.getItem(i);
                usedLeft += model.leftAddonMargin;
                childView.layout(usedLeft, getPaddingTop(),
                        usedLeft + childMeasureWidth, b - t - getPaddingBottom());


                int oldLeft, oldWidth, newLeft, newWidth;
                oldLeft = model.contentLeft;
                oldWidth = model.contentWidth;
                if (mMode == MODE_FIXED && (mIndicator != null && mIndicator.isIndicatorWidthFollowContent())) {
                    newLeft = usedLeft + childView.getContentViewLeft();
                    newWidth = childView.getContentViewWidth();
                } else {
                    newLeft = usedLeft;
                    newWidth = childMeasureWidth;
                }
                if (oldLeft != newLeft || oldWidth != newWidth) {
                    model.contentLeft = newLeft;
                    model.contentWidth = newWidth;
                }
                usedLeft = usedLeft + childMeasureWidth + model.rightAddonMargin +
                        (mMode == MODE_SCROLLABLE ? mItemSpaceInScrollMode : 0);
            }

            if (mCurrentSelectedIndex != NO_POSITION && mSelectAnimator == null
                    && !needPreventEvent()) {
                layoutIndicator(mTabAdapter.getItem(mCurrentSelectedIndex), false);
            }
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (mIndicator != null && (!mHideIndicatorWhenTabCountLessTwo || mTabAdapter.getSize() > 1)) {
                mIndicator.draw(canvas, getPaddingTop(), getHeight() - getPaddingBottom());
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



}

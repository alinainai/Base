package com.gas.test.utils.view.tablayoutkt.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

import com.gas.test.utils.view.tablayoutkt.utils.TabUtils;

public class QMUITabView extends FrameLayout{
    private static final String TAG = "QMUITabView";
    private QMUITab mTab;
    private QMUICollapsingTextHelper mCollapsingTextHelper;
    private Interpolator mPositionInterpolator;
    private GestureDetector mGestureDetector;
    private Callback mCallback;

    private float mCurrentTextLeft = 0;
    private float mCurrentTextTop = 0;

    private float mCurrentTextWidth = 0;
    private float mCurrentTextHeight = 0;

    private float mNormalTextLeft = 0;
    private float mNormalTextTop = 0;
    private float mSelectedTextLeft = 0;
    private float mSelectedTextTop = 0;

    private TextView mSignCountView;

    public QMUITabView(@NonNull Context context) {
        super(context);
        
        // 使得每个tab可被诸如TalkBack等屏幕阅读器聚焦
        // 这样视力受损用户（如盲人、低、弱视力）就能与tab交互
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        
        setWillNotDraw(false);
        mCollapsingTextHelper = new QMUICollapsingTextHelper(this, 1f);
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mCallback != null) {
                    mCallback.onDoubleClick(QMUITabView.this);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mCallback != null) {
                    mCallback.onClick(QMUITabView.this);
                    return false;
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return mCallback != null;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mCallback != null) {
                    mCallback.onLongClick(QMUITabView.this);
                }
            }
        });
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setPositionInterpolator(Interpolator positionInterpolator) {
        mPositionInterpolator = positionInterpolator;
        mCollapsingTextHelper.setPositionInterpolator(positionInterpolator);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    public void bind(QMUITab tab) {
        mCollapsingTextHelper.setTextSize(tab.normalTextSize, tab.selectedTextSize, false);
        mCollapsingTextHelper.setTypeface(tab.normalTypeface, tab.selectedTypeface, false);
        mCollapsingTextHelper.setTypefaceUpdateAreaPercent(tab.typefaceUpdateAreaPercent);
        int gravity = Gravity.LEFT | Gravity.TOP;
        mCollapsingTextHelper.setGravity(gravity, gravity, false);
        mCollapsingTextHelper.setText(tab.getText());
        mTab = tab;
        boolean hasRedPoint = mTab.signCount == QMUITab.RED_POINT_SIGN_COUNT;
        boolean hasSignCount = mTab.signCount > 0;
        if (hasRedPoint || hasSignCount) {
            ensureSignCountView(getContext());

            LayoutParams signCountLp = (LayoutParams) mSignCountView.getLayoutParams();
            if (hasSignCount) {
                mSignCountView.setText(
                        TabUtils.formatNumberToLimitedDigits(mTab.signCount, mTab.signCountDigits));
                mSignCountView.setMinWidth(TabUtils.dpToPx(getContext(),14));
                signCountLp.height = TabUtils.dpToPx(getContext(),14);
            } else {
                mSignCountView.setText(null);
                int redPointSize = TabUtils.dpToPx(getContext(),6);
                signCountLp.width = redPointSize;
                signCountLp.height = redPointSize;
            }
            mSignCountView.setLayoutParams(signCountLp);
            mSignCountView.setVisibility(View.VISIBLE);
        } else {
            if (mSignCountView != null) {
                mSignCountView.setVisibility(View.GONE);
            }
        }
        requestLayout();
    }

    public void setSelectFraction(float fraction) {
        fraction = TabUtils.constrain(fraction, 0f, 1f);
        updateCurrentInfo(fraction);
        mCollapsingTextHelper.setExpansionFraction(1 - fraction);
        if (mSignCountView != null) {
            Point point = calculateSignCountLayoutPosition();
            int x = point.x, y = point.y;
            if (point.x + mSignCountView.getMeasuredWidth() > getMeasuredWidth()) {
                x = getMeasuredWidth() - mSignCountView.getMeasuredWidth();
            }

            if (point.y - mSignCountView.getMeasuredHeight() < 0) {
                y = mSignCountView.getMeasuredHeight();
            }
            ViewCompat.offsetLeftAndRight(mSignCountView, x - mSignCountView.getLeft());
            ViewCompat.offsetTopAndBottom(mSignCountView, y - mSignCountView.getBottom());
        }
    }

    private void updateCurrentInfo(float fraction) {
        mCurrentTextLeft = QMUICollapsingTextHelper.lerp(
                mNormalTextLeft, mSelectedTextLeft, fraction, mPositionInterpolator);
        mCurrentTextTop = QMUICollapsingTextHelper.lerp(
                mNormalTextTop, mSelectedTextTop, fraction, mPositionInterpolator);

        float normalTextWidth = mCollapsingTextHelper.getCollapsedTextWidth();
        float normalTextHeight = mCollapsingTextHelper.getCollapsedTextHeight();
        float selectedTextWidth = mCollapsingTextHelper.getExpandedTextWidth();
        float selectedTextHeight = mCollapsingTextHelper.getExpandedTextHeight();
        mCurrentTextWidth = QMUICollapsingTextHelper.lerp(
                normalTextWidth, selectedTextWidth, fraction, mPositionInterpolator);
        mCurrentTextHeight = QMUICollapsingTextHelper.lerp(
                normalTextHeight, selectedTextHeight, fraction, mPositionInterpolator);
    }

    public int getContentViewWidth() {
        if (mTab == null) {
            return 0;
        }
        float textWidth = mCollapsingTextHelper.getExpandedTextWidth();
        return (int) (textWidth + 0.5);
    }

    public int getContentViewLeft() {
        if (mTab == null) {
            return 0;
        }
        return (int) (mSelectedTextLeft + 0.5);
    }

    private TextView ensureSignCountView(Context context) {
        if (mSignCountView == null) {
            mSignCountView = createSignCountView(context);
            LayoutParams signCountLp;
            if (mSignCountView.getLayoutParams() != null) {
                signCountLp = new LayoutParams(mSignCountView.getLayoutParams());
            } else {
                signCountLp = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            addView(mSignCountView, signCountLp);
        }
        return mSignCountView;
    }

    protected TextView createSignCountView(Context context) {
        TextView btn = new TextView(context);
        btn.setTextColor(Color.WHITE);
        btn.setBackgroundColor(Color.RED);
        return btn;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mTab == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        onMeasureTab(widthSize, heightSize);
        int useWidthMeasureSpec = widthMeasureSpec;
        int useHeightMeasureSpec = heightMeasureSpec;
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) mCollapsingTextHelper.getExpandedTextWidth();
            if(mSignCountView != null && mSignCountView.getVisibility() != View.GONE){
                mSignCountView.measure(0, 0);
                widthSize = Math.max(widthSize,
                        widthSize + mSignCountView.getMeasuredWidth() + mTab.signCountLeftMarginWithIconOrText);
            }
            useWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) mCollapsingTextHelper.getExpandedTextHeight();
            useHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(useWidthMeasureSpec, useHeightMeasureSpec);
    }

    protected void onMeasureTab(int widthSize, int heightSize) {
        int textWidth = widthSize, textHeight = heightSize;
        mCollapsingTextHelper.setCollapsedBounds(0, 0, textWidth, textHeight);
        mCollapsingTextHelper.setExpandedBounds(0, 0, textWidth, textHeight);
        mCollapsingTextHelper.calculateBaseOffsets();
    }

    @Override
    protected final void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        onLayoutTab(right - left, bottom - top);
        onLayoutSignCount(right - left, bottom - top);
    }

    protected void onLayoutSignCount(int width, int height) {
        if (mSignCountView != null && mTab != null) {
            Point point = calculateSignCountLayoutPosition();
            int x = point.x, y = point.y;
            if (point.x + mSignCountView.getMeasuredWidth() > width) {
                x = width - mSignCountView.getMeasuredWidth();
            }

            if (point.y - mSignCountView.getMeasuredHeight() < 0) {
                y = mSignCountView.getMeasuredHeight();
            }
            mSignCountView.layout(x, y - mSignCountView.getMeasuredHeight(),
                    x + mSignCountView.getMeasuredWidth(), y);
        }
    }

    private Point calculateSignCountLayoutPosition() {
        int left, bottom;
        left = (int) (mCurrentTextLeft + mCurrentTextWidth);
        bottom = (int) (mCurrentTextTop);
        Point point = new Point(left, bottom);
        int verticalOffset = mTab.signCountBottomMarginWithIconOrText;
        if(verticalOffset == QMUITab.SIGN_COUNT_VIEW_LAYOUT_VERTICAL_CENTER && mSignCountView != null){
            point.y = getMeasuredHeight() - (getMeasuredHeight() - mSignCountView.getMeasuredHeight()) / 2;
            point.offset(mTab.signCountLeftMarginWithIconOrText, 0);
        }else{
            point.offset(mTab.signCountLeftMarginWithIconOrText, verticalOffset);
        }
        return point;
    }

    protected void onLayoutTab(int width, int height) {
        if (mTab == null) {
            return;
        }
        mCollapsingTextHelper.calculateCurrentOffsets();
        float normalTextWidth = mCollapsingTextHelper.getCollapsedTextWidth();
        float normalTextHeight = mCollapsingTextHelper.getCollapsedTextHeight();

        float selectedTextWidth = mCollapsingTextHelper.getExpandedTextWidth();
        float selectedTextHeight = mCollapsingTextHelper.getExpandedTextHeight();

        switch (mTab.gravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.BOTTOM:
                mNormalTextTop = height - normalTextHeight;
                mSelectedTextTop = height - selectedTextHeight;
                break;
            case Gravity.TOP:
                mNormalTextTop = 0;
                mSelectedTextTop = 0;
                break;
            case Gravity.CENTER_VERTICAL:
            default:
                mNormalTextTop = (height - normalTextHeight) / 2;
                mSelectedTextTop = (height - selectedTextHeight) / 2;
                break;
        }

        switch (mTab.gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case Gravity.RIGHT:
                mNormalTextLeft = width - normalTextWidth;
                mSelectedTextLeft = width - selectedTextWidth;
                break;
            case Gravity.LEFT:
                mNormalTextLeft = 0;
                mSelectedTextLeft = 0;
                break;
            case Gravity.CENTER_HORIZONTAL:
            default:
                mNormalTextLeft = (width - normalTextWidth) / 2;
                mSelectedTextLeft = (width - selectedTextWidth) / 2;
                break;
        }
        updateCurrentInfo(1 - mCollapsingTextHelper.getExpansionFraction());
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable drawable) {
        invalidate();
    }

    @Override
    public final void draw(Canvas canvas) {
        onDrawTab(canvas);
        super.draw(canvas);
    }
    
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);

        // 给每个tab添加文本标签
        // 使得TalkBack等屏幕阅读器focus 到 tab上时可将tab的文本通过TTS朗读出来
        // 这样视力受损用户（如盲人、低、弱视力）就能和widget交互
        info.setContentDescription(mTab.getText());
    }

    protected void onDrawTab(Canvas canvas) {
        if (mTab == null) {
            return;
        }
        canvas.save();
        canvas.translate(mCurrentTextLeft, mCurrentTextTop);
        mCollapsingTextHelper.draw(canvas);
        canvas.restore();
    }



    public interface Callback {
        void onClick(QMUITabView view);

        void onDoubleClick(QMUITabView view);

        void onLongClick(QMUITabView view);
    }
}

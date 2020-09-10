package com.gas.test.utils.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


import com.gas.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义折线图
 */
public class ChartView extends View {
    private static final int FIRST = 1;
    private static final int MIDDLE = 2;
    private static final int END = 3;
    //xy坐标轴颜色
    private int xyLineColor = 0xffCFE2CF;
    //折线选中的圆形颜色
    private int selectCircleColor = 0xff00A8FF;
    //选中数据提示框颜色
    private int selectReminderColor = 0xff00A8FF;
    //折线中圆形内部部颜色
    private int xyTextColor = 0xff0014FF;
    //折线图中折线的颜色
    private int lineColor = 0xffFD00FF;
    //xy坐标轴宽度
    private int xyLineWidth = dpToPx(1);
    //xy坐标轴文字大小
    private int xyTextSize = spToPx(12);
    //x轴各个坐标点水平间距
    private int interval = dpToPx(40);
    //背景颜色
    private int bgColor = 0xffffffff;
    //是否有起手时的滑动感
    private boolean isScroll = false;
    //提示框显示位置
    private int mShowPositionType = 3;
    //绘制XY轴坐标对应的画笔
    private Paint mXYPaint;
    //绘制XY轴的文本对应的画笔
    private Paint mXYTextPaint;
    //画折线对应的画笔
    private Paint mSpinnerLinePaint;
    private int width;
    private int height;
    //x轴的原点坐标
    private int mXOri;
    //y轴的原点坐标
    private int mYOri;
    //第一个点X的坐标
    private float mXInit;
    //第一个点对应的最大X坐标
    private float maxXInit;
    //第一个点对应的最小X坐标
    private float minXInit;
    //x轴坐标对应的数据
    private List<String> mXData = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> mYData = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> mSpinnerValue = new HashMap<>();
    //点击的点对应的X轴的第几个点，默认1
    private int selectIndex = 1;
    //X轴刻度文本对应的最大矩形，为了选中时，在x轴文本画的框框大小一致，获取从数据中得到的x轴数据，获得最长数据
    private Rect xValueRect;
    //速度检测器
    private VelocityTracker mTracker;
    //是否为短距离滑动
    private boolean isShortSlide = false;
    //获取尺寸的的中间
    private int mSelectMiddle = 0;
    //曲线切率
    private float mLineSmoothness = 0.18f;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    //设置切率
    public void setLineSmoothness(float lineSmoothness) {
        if (lineSmoothness != this.mLineSmoothness) {
            this.mLineSmoothness = lineSmoothness;
        }
    }

    /**
     * 初始化
     */
    private void initPaint() {
        mXYPaint = new Paint();
        mXYPaint.setAntiAlias(true);
        mXYPaint.setStrokeWidth(xyLineWidth);
        mXYPaint.setStrokeJoin(Paint.Join.ROUND);
        mXYPaint.setColor(xyLineColor);

        mXYTextPaint = new Paint();
        mXYTextPaint.setAntiAlias(true);
        mXYTextPaint.setTextSize(xyTextSize);
        mXYTextPaint.setStrokeJoin(Paint.Join.ROUND);
        mXYTextPaint.setColor(xyTextColor);
        mXYTextPaint.setStyle(Paint.Style.STROKE);

        mSpinnerLinePaint = new Paint();
        mSpinnerLinePaint.setAntiAlias(true);
        mSpinnerLinePaint.setStrokeWidth(xyLineWidth);
        mSpinnerLinePaint.setColor(lineColor);
        mSpinnerLinePaint.setStyle(Paint.Style.STROKE);
        mSpinnerLinePaint.setStrokeJoin(Paint.Join.ROUND);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChartView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.ChartView_xy_line_color) {
                xyLineColor = array.getColor(attr, xyLineColor);

            } else if (attr == R.styleable.ChartView_xy_line_width) {
                xyLineWidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xyLineWidth, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.ChartView_xy_text_color) {
                xyTextColor = array.getColor(attr, xyTextColor);

            } else if (attr == R.styleable.ChartView_xy_text_size) {
                xyTextSize = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xyTextSize, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.ChartView_line_color) {
                lineColor = array.getColor(attr, lineColor);

            } else if (attr == R.styleable.ChartView_interval) {
                interval = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, interval, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.ChartView_bg_color) {
                bgColor = array.getColor(attr, bgColor);

            } else if (attr == R.styleable.ChartView_select_circle_color) {
                selectCircleColor = array.getColor(attr, selectCircleColor);

            } else if (attr == R.styleable.ChartView_select_reminder_color) {
                selectReminderColor = array.getColor(attr, selectReminderColor);

            } else if (attr == R.styleable.ChartView_isScroll) {
                isScroll = array.getBoolean(attr, isScroll);
            } else if (attr == R.styleable.ChartView_show_position) {
                mShowPositionType = array.getInt(attr, mShowPositionType);
            }
        }
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            width = getWidth();
            height = getHeight();
            //Y轴文本的最大宽度
            float textYWdith = getTextBounds(mYData.get(getListItemMaxIndex(mYData)) + "", mXYTextPaint).width();
            for (int i = 0; i < mYData.size(); i++) {//求取y轴文本最大的宽度
                float temp = getTextBounds(mYData.get(i) + "", mXYTextPaint).width();
                if (temp > textYWdith)
                    textYWdith = temp;
            }
            int dp2 = dpToPx(2);
            int dp3 = dpToPx(3);
            mXOri = (int) (dp2 + textYWdith + dp2 + xyLineWidth);
            //获取x轴的最长文本的宽度所占的矩形
            xValueRect = getTextBounds(mXData.get(getListItemMaxIndex(mXData)), mXYTextPaint);
            //X轴文本高度
            float textXHeight = xValueRect.height();
            for (int i = 0; i < mXData.size(); i++) {
                Rect rect = getTextBounds(mXData.get(i) + "", mXYTextPaint);
                if (rect.height() > textXHeight)
                    textXHeight = rect.height();
                if (rect.width() > xValueRect.width())
                    xValueRect = rect;
            }
            mYOri = (int) (height - dp2 - textXHeight - dp3 - xyLineWidth);
            mXInit = mXOri + xValueRect.width() / 2 + dpToPx(5);
            minXInit = width - (width - mXOri) * 0.1f - interval * (mXData.size() - 1);
            maxXInit = mXInit;
        }
        selectIndex = getSelectIndexFromShowType(mShowPositionType);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(bgColor);
        drawXY(canvas);
        drawBrokenLineAndPoint(canvas);
    }

    /**
     * 绘制交点处对应的点
     */
    private void drawBrokenLineAndPoint(Canvas canvas) {
        if (mXData.size() <= 0)
            return;
        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        drawBrokenLine(canvas);
        drawBrokenPoint(canvas);
        // 将超出x轴坐标的部分截掉
        mSpinnerLinePaint.setStyle(Paint.Style.FILL);
        mSpinnerLinePaint.setColor(bgColor);
        mSpinnerLinePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        RectF rectF = new RectF(0, 0, mXOri, height);
        canvas.drawRect(rectF, mSpinnerLinePaint);
        mSpinnerLinePaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    /**
     * 绘制曲线对应的点
     */
    private void drawBrokenPoint(Canvas canvas) {
        float dp2 = dpToPx(2);
        float dp4 = dpToPx(4);
        float dp7 = dpToPx(7);
        Log.e("selectIndex", "index:" + selectIndex);
        //绘制节点
        for (int i = 0; i < mXData.size(); i++) {
            float x = mXInit + interval * i;
            float y = mYOri - mYOri * (1 - 0.1f) * mSpinnerValue.get(mXData.get(i)) / mYData.get(mYData.size() - 1);
            //绘制选中点
            if (i == selectIndex - 1) {
                mSpinnerLinePaint.setStyle(Paint.Style.FILL);
                //设置选中颜色
                mSpinnerLinePaint.setColor(selectCircleColor);
                canvas.drawCircle(x, y, dp7, mSpinnerLinePaint);
                mSpinnerLinePaint.setColor(selectReminderColor);
                canvas.drawCircle(x, y, dp4, mSpinnerLinePaint);
                drawFloatTextBox(canvas, x, y - dp7, mSpinnerValue.get(mXData.get(i)));
            }
            //绘制普通节点
            mSpinnerLinePaint.setStyle(Paint.Style.FILL);
            mSpinnerLinePaint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, dp2, mSpinnerLinePaint);
            mSpinnerLinePaint.setStyle(Paint.Style.STROKE);
            mSpinnerLinePaint.setColor(lineColor);
            canvas.drawCircle(x, y, dp2, mSpinnerLinePaint);

        }
    }

    /**
     * 绘制浮动框
     * */
    private void drawFloatTextBox(Canvas canvas, float x, float y, int text) {
        int dp6 = dpToPx(6);
        int dp18 = dpToPx(18);
        //p1
        Path path = new Path();
        path.moveTo(x, y);
        //p2
        path.lineTo(x - dp6, y - dp6);
        //p3
        path.lineTo(x - dp18, y - dp6);
        //p4
        path.lineTo(x - dp18, y - dp6 - dp18);
        //p5
        path.lineTo(x + dp18, y - dp6 - dp18);
        //p6
        path.lineTo(x + dp18, y - dp6);
        //p7
        path.lineTo(x + dp6, y - dp6);
        //p1
        path.lineTo(x, y);
        canvas.drawPath(path, mSpinnerLinePaint);
        mSpinnerLinePaint.setColor(Color.WHITE);
        mSpinnerLinePaint.setTextSize(spToPx(14));
        Rect rect = getTextBounds(text + "", mSpinnerLinePaint);
        canvas.drawText(text + "", x - rect.width() / 2, y - dp6 - (dp18 - rect.height()) / 2, mSpinnerLinePaint);
    }

    /**
     * 绘制平滑曲线
     */
    private void drawBrokenLine(Canvas canvas) {
        mSpinnerLinePaint.setStyle(Paint.Style.STROKE);
        mSpinnerLinePaint.setColor(lineColor);
        //绘制折线
        Path path = new Path();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;
        int lineSize = mXData.size();
        for (int i = 0; i < lineSize; i++) {
            float x;
            float y;
            if (Float.isNaN(currentPointX)) {
                currentPointX = getSpinnerPoint(i).x;
                currentPointY = getSpinnerPoint(i).y;
            }
            if (Float.isNaN(previousPointX)) {
                //是第一个点?
                if (i > 0) {
                    previousPointX = getSpinnerPoint(i - 1).x;
                    previousPointY = getSpinnerPoint(i - 1).y;
                } else {
                    //用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是前两个点?
                if (i > 1) {
                    prePreviousPointX = getSpinnerPoint(i - 2).x;
                    prePreviousPointY = getSpinnerPoint(i - 2).y;
                } else {
                    //当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (i < lineSize - 1) {
                nextPointX = getSpinnerPoint(i + 1).x;
                nextPointY = getSpinnerPoint(i + 1).y;
            } else {
                //用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (i == 0) {
                // 将Path移动到开始点
                path.moveTo(currentPointX, currentPointY);
            } else {
                // 求出控制点坐标
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX = previousPointX + (mLineSmoothness * firstDiffX);
                final float firstControlPointY = previousPointY + (mLineSmoothness * firstDiffY);
                final float secondControlPointX = currentPointX - (mLineSmoothness * secondDiffX);
                final float secondControlPointY = currentPointY - (mLineSmoothness * secondDiffY);
                //画出曲线
                path.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                        currentPointX, currentPointY);
            }

            // 更新
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        canvas.drawPath(path, mSpinnerLinePaint);
    }

    /**
     * 绘制XY坐标
     */
    private void drawXY(Canvas canvas) {
        int length = dpToPx(5);//刻度的长度
        //绘制Y坐标
        canvas.drawLine(mXOri - xyLineWidth / 2, 0, mXOri - xyLineWidth / 2, mYOri, mXYPaint);
        //绘制箭头
        mXYPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(mXOri - xyLineWidth / 2 - dpToPx(5), dpToPx(12));
        path.lineTo(mXOri - xyLineWidth / 2, xyLineWidth / 2);
        path.lineTo(mXOri - xyLineWidth / 2 + dpToPx(5), dpToPx(12));
        canvas.drawPath(path, mXYPaint);
        //绘制刻度
        int yLength = (int) (mYOri * (1 - 0.1f) / (mYData.size() - 1));//y轴上面空出10%,计算出y轴刻度间距
        for (int i = 0; i < mYData.size(); i++) {
            //绘制刻度
            canvas.drawLine(mXOri, mYOri - yLength * i + xyLineWidth / 2, mXOri + length, mYOri - yLength * i + xyLineWidth / 2, mXYPaint);
            mXYTextPaint.setColor(xyTextColor);
            //绘制文本
            String text = mYData.get(i) + "";
            Rect rect = getTextBounds(text, mXYTextPaint);
            canvas.drawText(text, 0, text.length(), mXOri - xyLineWidth - dpToPx(2) - rect.width(), mYOri - yLength * i + rect.height() / 2, mXYTextPaint);
        }
        //绘制坐标
        canvas.drawLine(mXOri, mYOri + xyLineWidth / 2, width, mYOri + xyLineWidth / 2, mXYPaint);
        //绘制箭头
        mXYPaint.setStyle(Paint.Style.STROKE);
        path = new Path();
        //整个长度
        float xLength = mXInit + interval * (mXData.size() - 1) + (width - mXOri) * 0.1f;
        if (xLength < width)
            xLength = width;
        path.moveTo(xLength - dpToPx(12), mYOri + xyLineWidth / 2 - dpToPx(5));
        path.lineTo(xLength - xyLineWidth / 2, mYOri + xyLineWidth / 2);
        path.lineTo(xLength - dpToPx(12), mYOri + xyLineWidth / 2 + dpToPx(5));
        canvas.drawPath(path, mXYPaint);
        //绘制x轴刻度
        for (int i = 0; i < mXData.size(); i++) {
            float x = mXInit + interval * i;
            if (x >= mXOri) {//只绘制从原点开始的区域
                mXYTextPaint.setColor(xyTextColor);
                canvas.drawLine(x, mYOri, x, mYOri - length, mXYPaint);
                //绘制X轴文本
                String text = mXData.get(i);
                Rect rect = getTextBounds(text, mXYTextPaint);
                if (i == selectIndex - 1) {
                    mXYTextPaint.setColor(lineColor);
                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2, mYOri + xyLineWidth + dpToPx(2) + rect.height(), mXYTextPaint);
                    canvas.drawRoundRect(x - xValueRect.width() / 2 - dpToPx(3), mYOri + xyLineWidth + dpToPx(1), x + xValueRect.width() / 2 + dpToPx(3), mYOri + xyLineWidth + dpToPx(2) + xValueRect.height() + dpToPx(2), dpToPx(2), dpToPx(2), mXYTextPaint);
                } else {
                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2, mYOri + xyLineWidth + dpToPx(2) + rect.height(), mXYTextPaint);
                }
            }
        }
    }

    private float startX;
    private float startx;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrolling)
            return super.onTouchEvent(event);
        //当该view获得点击事件，就请求父控件不拦截事件
        this.getParent().requestDisallowInterceptTouchEvent(true);
        obtainVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startx = event.getX();
                Log.e("XXXX", "down:" + startX + "");
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动距离小于等于8的时候任务为短距离滑动
                //当前x轴的尺寸与设置的x轴间隔的距离之乘积大于 屏幕中的显示布局宽度与x轴七点之差时，开始移动
                if (interval * mXData.size() > width - mXOri) {
                    //获取滑动的距离
                    float dis = event.getX() - startX;
                    //重新赋值给startX
                    startX = event.getX();
                    //当前x原点距离与左右滑动的距离之和没有最小值大，则将当前x距离赋值为最小，以下相似
                    if (mXInit + dis < minXInit) {
                        mXInit = minXInit;
                    } else if (mXInit + dis > maxXInit) {
                        mXInit = maxXInit;
                    } else {
                        mXInit = mXInit + dis;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isShortSlide = Math.abs(event.getX() - startx) <= dpToPx(8);
                clickAction(event);
                scrollAfterActionUp();
                this.getParent().requestDisallowInterceptTouchEvent(false);
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                //增加这行代码防止与父类的滑动事件冲突
                this.getParent().requestDisallowInterceptTouchEvent(false);
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    //是否正在滑动
    private boolean isScrolling = false;

    /**
     * 手指抬起后的滑动处理
     */
    private void scrollAfterActionUp() {
        if (!isScroll)
            return;
        final float velocity = getVelocity();
        float scrollLength = maxXInit - minXInit;
        if (Math.abs(velocity) < 10000)
            scrollLength = (maxXInit - minXInit) * Math.abs(velocity) / 10000;
        ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
        animator.setDuration((long) (scrollLength / (maxXInit - minXInit) * 1000));//时间最大为1000毫秒，此处使用比例进行换算
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (velocity < 0 && mXInit > minXInit) {//向左滑动
                    if (mXInit - value <= minXInit)
                        mXInit = minXInit;
                    else
                        mXInit = mXInit - value;
                } else if (velocity > 0 && mXInit < maxXInit) {//向右滑动
                    if (mXInit + value >= maxXInit)
                        mXInit = maxXInit;
                    else
                        mXInit = mXInit + value;
                }
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isScrolling = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    /**
     * 获取速度
     *
     * @return
     */
    private float getVelocity() {
        if (mTracker != null) {
            mTracker.computeCurrentVelocity(1000);
            return mTracker.getXVelocity();
        }
        return 0;
    }

    /**
     * 点击X轴坐标或者折线节点
     *  */
    // 44  142  139
    private void clickAction(MotionEvent event) {
        int dp8 = dpToPx(8);
        float eventX = event.getX();
        float eventY = event.getY();
        if (!isShortSlide) {
            for (int i = 0; i < mXData.size(); i++) {
                float x = mXInit + interval * i;
                float start = mXOri;
                if (x >= start + (mSelectMiddle - 1) * interval && x < start + mSelectMiddle * interval) {
                    selectIndex = i + 1;
                    invalidate();
                }
            }
            return;
        }
        for (int i = 0; i < mXData.size(); i++) {
            //节点
            float x = mXInit + interval * i;
            float y = mYOri - mYOri * (1 - 0.1f) * mSpinnerValue.get(mXData.get(i)) / mYData.get(mYData.size() - 1);
            if (eventX >= x - dp8 && eventX <= x + dp8 &&
                    eventY >= y - dp8 && eventY <= y + dp8 && selectIndex != i + 1) {//每个节点周围范围内都是可点击区域
                selectIndex = i + 1;
                invalidate();
                return;
            }
            //X轴刻度
            String text = mXData.get(i);
            Rect rect = getTextBounds(text, mXYTextPaint);
            x = mXInit + interval * i;
            y = mYOri + xyLineWidth + dpToPx(2);
            if (eventX >= x - rect.width() / 2 - dp8 && eventX <= x + rect.width() + dp8 / 2 &&
                    eventY >= y - dp8 && eventY <= y + rect.height() + dp8 && selectIndex != i + 1) {
                selectIndex = i + 1;
                invalidate();
                return;
            }
        }
    }


    /**
     * 获取速度跟踪器
     *
     * @param event
     */
    private void obtainVelocityTracker(MotionEvent event) {
        if (!isScroll)
            return;
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);
    }

    /**
     * 回收速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (mTracker != null) {
            mTracker.recycle();
            mTracker = null;
        }
    }

    /**
     * 根据用户输入显示类型，在滑动时在不同的位置显示提示框
     */
    private int getSelectIndexFromShowType(int showPositionType) {
        int visibleScale = (width - mXOri) / interval;
        switch (showPositionType) {
            case FIRST:
                mSelectMiddle = 1;
                return mSelectMiddle;
            case MIDDLE:
                if (mXData.size() <= visibleScale) {
                    mSelectMiddle = middleIndex(mXData.size());
                } else {
                    mSelectMiddle = middleIndex(visibleScale);
                }
                return mSelectMiddle;  //屏幕可显示的刻度
            case END:
                if (mXData.size() <= visibleScale) {
                    mSelectMiddle = mXData.size();
                } else {
                    mSelectMiddle = visibleScale;
                }
                return visibleScale;
            default:
                mSelectMiddle = 0;
                return mSelectMiddle;
        }
    }

    public void setValue(Map<String, Integer> value) {
        this.mSpinnerValue = value;
        invalidate();
    }

    public void setValue(Map<String, Integer> value, List<String> xValue, List<Integer> yValue) {
        this.mSpinnerValue = value;
        this.mXData = xValue;
        this.mYData = yValue;
        invalidate();
    }


    public Map<String, Integer> getValue() {
        return mSpinnerValue;
    }

    /**
     * 获取丈量文本的矩形
     *
     * @param text
     * @param paint
     * @return
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * sp转化为px
     *
     * @param sp
     * @return
     */
    private int spToPx(int sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f * (sp >= 0 ? 1 : -1));
    }

    /**
     * 获取集合中最长的index
     */
    private static final int NULL_INDEX = -1;

    public int getListItemMaxIndex(List<?> data) {
        if (data == null || data.size() < 1) {
            return NULL_INDEX;
        }
        int max = (data.get(0) + "").length();
        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i) + "";
            if (s.length() > max) {
                return i;
            }
        }
        return NULL_INDEX;
    }

    //获得在滑动结束的时候在屏幕内的点
    private int middleIndex(int size) {
        if (size % 2 == 0) {
            return size / 2;
        } else {
            return size / 2 + 1;
        }
    }


    /**
     * 根据两点坐标获取中间某个点
     *
     * @param from 坐标1
     * @param to   坐标2
     */

    //获取已知点的斜率 y = kx+b
    private float getSlope(Point from, Point to) {
        float k = (to.y - from.y) / (to.x - from.x);
        Log.e("Point", "参数b：" + k);
        return k;
    }

    //获取参数 b
    private float getParams(Point from, Point to) {
        float b = from.y - (getSlope(from, to) * from.x);
        Log.e("Point", "参数b：" + b);
        return b;
    }

    //根据两点间的坐标获取x轴的任意一个坐标x值,
    private float getArbitrarilyX(Point from, Point to, int grade, int needGrade) {
        //获得输入的新坐标
        float x = ((to.x - from.x) * needGrade) / grade + from.x;
        Log.e("Point", "x坐标值：" + x);
        return x;
    }

    //获取坐标值
    private Point getPoint(Point from, Point to, int grade, int needGrade) {
        Point point = new Point();
        point.setX(getArbitrarilyX(from, to, grade, needGrade));
        float slope = getSlope(from, to);
        point.setY(slope * point.x + getParams(from, to));
        return point;
    }

    //获取绘制折线的点
    private Point getSpinnerPoint(int valueIndex) {
        float x = mXInit + interval * (valueIndex);
        float y = mYOri - mYOri * (1 - 0.1f) * mSpinnerValue.get(mXData.get(valueIndex)) / mYData.get(mYData.size() - 1);
        return new Point(x, y);
    }

    private class Point {
        float x;
        float y;

        public Point() {
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }}

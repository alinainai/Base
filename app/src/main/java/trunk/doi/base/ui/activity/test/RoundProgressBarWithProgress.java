package trunk.doi.base.ui.activity.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import trunk.doi.base.R;

/**
 * 作者：Mr.Lee on 2017-10-18 10:48
 * 邮箱：569932357@qq.com
 */

public class RoundProgressBarWithProgress extends HorizontalProgressbarWithProgress {


    private int mRadius=dp2px(30);
    private int mMaxPaintWidth;

    public RoundProgressBarWithProgress(Context context) {
        super(context);
        init(null);
    }

    public RoundProgressBarWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RoundProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundProgressBarWithProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs){

        mReachHeigh= (int) (mUnReachHeigh*2.5f);

        TypedArray array=getContext().obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWithProgress);
        mRadius=(int) array.getDimension(R.styleable.RoundProgressBarWithProgress_radius,mRadius);
        array.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mMaxPaintWidth=Math.max(mReachHeigh,mUnReachHeigh);
        //默认4个padding一致
        int except=mRadius*2+mMaxPaintWidth+getPaddingLeft()+getPaddingRight();
        int width=resolveSize(except,widthMeasureSpec);
        int height=resolveSize(except,heightMeasureSpec);
        int realWidth=Math.min(width,height);

        mRadius=(realWidth-getPaddingLeft()-getPaddingRight()-mMaxPaintWidth)/2;

        setMeasuredDimension(realWidth,realWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        String text=getProgress()+"%";
        float textWidth=mPaint.measureText(text);
        float textHeight=(mPaint.ascent()+mPaint.descent())/2;

        canvas.save();
        canvas.translate(getPaddingLeft(),getPaddingTop());

        mPaint.setStyle(Paint.Style.STROKE);
        // draw unreachbar
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeigh);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
        //draw reach bar
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeigh);
        float sweepAngle=getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,mRadius*2,mRadius*2),0,sweepAngle,false,mPaint);
        //draw text
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,mRadius-textWidth/2,mRadius-textHeight,mPaint);

        canvas.restore();

    }
}

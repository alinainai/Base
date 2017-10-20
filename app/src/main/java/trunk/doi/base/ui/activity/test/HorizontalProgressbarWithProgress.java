package trunk.doi.base.ui.activity.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import trunk.doi.base.R;

/**
 * 作者：Mr.Lee on 2017-10-17 15:51
 * 邮箱：569932357@qq.com
 */

public class HorizontalProgressbarWithProgress extends ProgressBar{

    private static final int DEFAULT_TEXT_SIZE=10;//sp
    private static final int DEFAULT_TEXT_COLOR=0xFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACH=0xFFD3D6DA;
    private static final int DEFAULT_HEIGHT_UNREACH=2;//dp
    private static final int DEFAULT_COLOR_REACH=DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_HEIGHT_REACH=2;
    private static final int DEFAULT_TEXT_OFFSET=10;

    protected int mTextSize=sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor=DEFAULT_TEXT_COLOR;
    protected int mUnReachColor=DEFAULT_COLOR_UNREACH;
    protected int mUnReachHeigh=dp2px(DEFAULT_HEIGHT_UNREACH);
    protected int mReachHeigh=dp2px(DEFAULT_HEIGHT_REACH);
    protected int mReachColor=DEFAULT_COLOR_REACH;
    protected int mTextOffset=dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint=new Paint();
    protected int mRealWidth;

    public HorizontalProgressbarWithProgress(Context context) {
        super(context);
        init(null);
    }

    public HorizontalProgressbarWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public HorizontalProgressbarWithProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HorizontalProgressbarWithProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }
    private void init(AttributeSet attrs) {


        /**
         * 获取dimension的方法有几种，区别不大
         * 共同点是都会将dp，sp的单位转为px，px单位的保持不变
         *
         * getDimension() 返回float，
         * getDimensionPixelSize 返回int 小数部分四舍五入
         * getDimensionPixelOffset 返回int，但是会抹去小数部分
         */
        TypedArray array=getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressbarWithProgress);

        mTextSize= (int) array.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_text_size,mTextSize);
        mTextColor=array.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_text_color,mTextColor);
        mUnReachColor=array.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_unreach_color,mUnReachColor);
        mUnReachHeigh=(int) array.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_unreach_height,mUnReachHeigh);
        mReachHeigh=(int) array.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_reach_height,mReachHeigh);
        mTextOffset=(int) array.getDimension(R.styleable.HorizontalProgressbarWithProgress_progress_text_offset,mTextOffset);
        mReachColor=array.getColor(R.styleable.HorizontalProgressbarWithProgress_progress_reach_color,mReachColor);

        array.recycle();

        mPaint.setTextSize(mTextSize);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int heigh=measureHeight(heightMeasureSpec);
        setMeasuredDimension(width,heigh);
        mRealWidth=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
    }



    private int measureHeight(int heightMeasureSpec) {

        int result=0;
        int mode=MeasureSpec.getMode(heightMeasureSpec);
        int size=MeasureSpec.getSize(heightMeasureSpec);

        if(mode==MeasureSpec.EXACTLY){
            result=size;
        }else{
            int textHeigh= (int) (mPaint.descent()-mPaint.ascent());
            result=getPaddingTop()+getPaddingBottom()+Math.max(Math.max(mReachHeigh,mUnReachHeigh),Math.abs(textHeigh));
            if(mode==MeasureSpec.AT_MOST){
                result=Math.min(result,size);
            }
        }
        return result;

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {


        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);

        boolean noNeedUnReach=false;
        String text=getProgress()+"%";
        int textWidth= (int) mPaint.measureText(text);
        float radio =getProgress()*1.0f/getMax();
        float progressX=radio*mRealWidth;
        if(progressX+textWidth>mRealWidth){
            progressX=mRealWidth-textWidth;
            noNeedUnReach=true;
        }

        float endX=progressX-mTextOffset/2;
        if(endX>0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeigh);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //draw text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent()+mPaint.ascent())/2);
        canvas.drawText(text,progressX,y,mPaint);


        //draw unreach bar
        if(!noNeedUnReach){
            float startX=progressX+ mTextOffset/2+textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeigh);
            canvas.drawLine(startX,0,mRealWidth,0,mPaint);
        }
        canvas.restore();


    }

    protected int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,getResources().getDisplayMetrics());
    }
    protected int sp2px(int spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal,getResources().getDisplayMetrics());
    }

}

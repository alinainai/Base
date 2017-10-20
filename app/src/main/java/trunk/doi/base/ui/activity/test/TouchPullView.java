package trunk.doi.base.ui.activity.test;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import trunk.doi.base.R;

/**
 * 作者：Mr.Lee on 2017-9-27 11:57
 * 邮箱：569932357@qq.com
 */

public class TouchPullView extends View {

    //圆的画笔
    private Paint mCirclePaint;
    //圆的半径
    private int mCircleRadius=50;
    private float mCirclePointX;
    private float mCirclePointY;
    private float mProgress;
    //可拖拽高度
    private int mDragHeigh=800;
    //目标宽度
    private int mTargetWidth=400;
    //贝塞尔曲线
    private Path mPath=new Path();
    private Paint mPathPaint;
    //重心点最终高度，决定控制点的Y坐标
    private int mTargetGravityHeight=10;
    //角度变换 0-135
    private int mTangentAngle=100;
    private Interpolator mProgressInterpolator=new DecelerateInterpolator();
    private Interpolator mTanentAngleInterpolator;

    private Drawable mContent=null;
    private int mContentMargin=0;


    public TouchPullView(Context context) {
        super(context);
        init(null);

    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs){


        final Context context=getContext();
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.TouchPullView,0,0);
        int color=array.getColor(R.styleable.TouchPullView_pColor,0x20000000);

        mCircleRadius=(int)array.getDimension(R.styleable.TouchPullView_pRadius,mCircleRadius);
        mDragHeigh=array.getDimensionPixelOffset(R.styleable.TouchPullView_pDragHeight,mDragHeigh);
        mTangentAngle=array.getInteger(R.styleable.TouchPullView_pTangentAngle,mTangentAngle);
        mTargetWidth=array.getDimensionPixelOffset(R.styleable.TouchPullView_pDragHeight,mTargetWidth);
        mTargetGravityHeight=array.getDimensionPixelOffset(R.styleable.TouchPullView_pTargetGravityHeight,mTargetGravityHeight);
        mContent=array.getDrawable(R.styleable.TouchPullView_pContentDrawable);
        mContentMargin=array.getDimensionPixelOffset(R.styleable.TouchPullView_pContentDrawableMargin,0);
        array.recycle();

        Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        p.setAntiAlias(true);
        //防抖动
        p.setDither(true);
        //填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);
        mCirclePaint=p;
        //初始化路径部分画笔
        p=new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        p.setAntiAlias(true);
        //防抖动
        p.setDither(true);
        //填充方式
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);
        mPathPaint=p;

        //切角路径插值器
        mTanentAngleInterpolator= PathInterpolatorCompat.create(
                (mCircleRadius*2.0f)/mDragHeigh,
                90.0f/mTangentAngle
        );

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       updatePathLayout();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int heighMode=MeasureSpec.getMode(heightMeasureSpec);
        int heigh=MeasureSpec.getSize(heightMeasureSpec);

        int iHeigh=(int)((mDragHeigh*mProgress+0.5)+ 2*mCircleRadius+getPaddingTop()+getPaddingBottom());
        int iWidth=2*mCircleRadius+getPaddingLeft()+getPaddingRight();
        int measureWidth,measureHeigh;

        if(widthMode==MeasureSpec.EXACTLY){
            measureWidth=width;
        }else if(widthMode==MeasureSpec.AT_MOST){
            measureWidth=Math.min(iWidth,width);
        }else{
            measureWidth=iWidth;
        }

        if(heighMode==MeasureSpec.EXACTLY){
            measureHeigh=heigh;
        }else if(heighMode==MeasureSpec.AT_MOST){
            measureHeigh=Math.min(iHeigh,heigh);
        }else{
            measureHeigh=iHeigh;
        }

        setMeasuredDimension(measureWidth,measureHeigh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count=canvas.save();
        float tranX=(getWidth()-getValueByLine(getWidth(),mTargetWidth,mProgress))/2;
        canvas.translate(tranX,0);
        canvas.drawPath(mPath,mPathPaint);
        //画圆
        canvas.drawCircle(mCirclePointX,mCirclePointY,mCircleRadius,mCirclePaint);
        Drawable drawable=mContent;
        if(drawable!=null){
            canvas.save();
            //剪切矩形区域
            canvas.clipRect(drawable.getBounds());
            //绘制
            drawable.draw(canvas);
            canvas.restore();
        }
        canvas.restoreToCount(count);



    }




    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(float progress){

        Log.e("TAG","progress="+progress);
        mProgress=progress;
        //重新请求测量
        requestLayout();


    }


    private void updatePathLayout(){

        final float progress=mProgressInterpolator.getInterpolation(mProgress);
        //获取可绘制区域高度宽度
        final float w=getValueByLine(getWidth(),mTargetWidth,mProgress);
        final float h=getValueByLine(0,mDragHeigh,mProgress);

        //X对称轴的参数，圆的圆心X
        final float cPointX=w/2;
        //圆的半径
        final  float cRadius=mCircleRadius;
        //圆的圆心Y坐标
        final float cPointY =h-cRadius;
        //控制点结束Y坐标
        final float endControlY=mTargetGravityHeight;

        mCirclePointX=cPointX;
        mCirclePointY= cPointY;

        final Path path=mPath;
        //重置
        path.reset();
        path.moveTo(0,0);

        //左边部分的结束点和控制点
        float lEndPointX,lEndPointY;
        float lControlPointX,lControlPointY;
        //角度转弧度

        float angle=mTangentAngle*mTanentAngleInterpolator.getInterpolation(progress);
        double radian=Math.toRadians(angle);
        float x=(float) (Math.sin(radian)*cRadius);
        float y=(float) (Math.cos(radian)*cRadius);

        lEndPointX=cPointX-x;
        lEndPointY= cPointY +y;

        //控制点y坐标变化
        lControlPointY=getValueByLine(0,endControlY,progress);
        //控制点与结束定之前的高度
        float tHeigh=lEndPointY-lControlPointY;
        //控制点与x坐标的距离
        float tWidth= (float) (tHeigh/Math.tan(radian));
        lControlPointX=lEndPointX-tWidth;
        //左边贝塞尔曲线
        path.quadTo(lControlPointX,lControlPointY,lEndPointX,lEndPointY);
        //连接到右边
        path.lineTo(cPointX+(cPointX-lEndPointX),lEndPointY);
        //右边贝塞尔曲线
        path.quadTo(cPointX+cPointX-lControlPointX,lControlPointY,w,0);
        //更新内容部分Drawable
        updateContentLayout(cPointX,cPointY,cRadius);

    }

    /**
     * 对内容部分进行测量并设置
     * @param cx
     * @param cy
     * @param radius
     */
    private void updateContentLayout(float cx,float cy,float radius){

        Drawable drawable=mContent;
        if(drawable!=null){
            int margin=mContentMargin;
            int l=(int)(cx-radius+margin);
            int r=(int)(cx+radius-margin);
            int t=(int)(cy-radius+margin);
            int b=(int)(cy+radius-margin);
            drawable.setBounds(l,t,r,b);
        }

    }

    //释放动画
    private ValueAnimator valueAnimator;

    /**
     * 添加释放动作
     */

    public void release(){

        if(valueAnimator==null){

            ValueAnimator animator=ValueAnimator.ofFloat(mProgress,0f);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    Object val=animation.getAnimatedValue();
                    if(val instanceof Float){
                        setProgress((Float) val);
                    }

                }
            });
            valueAnimator=animator;

        }else{

            valueAnimator.cancel();
            valueAnimator.setFloatValues(mProgress,0f);

        }
        valueAnimator.start();


    }

    /**
     * 获取当前值
     * @param start
     * @param end
     * @param progress
     * @return
     */
    private float getValueByLine(float start,float end ,float progress){
        return start+(end-start)*progress;
    }





}

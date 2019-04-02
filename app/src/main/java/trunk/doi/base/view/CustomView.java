package trunk.doi.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {


    private Paint mPaint;
    private Path mPath;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(36);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        //绘制圆形路径
//        mPath.addCircle(70, 70, 40, Path.Direction.CCW);    //添加逆时针的圆形路径
//        canvas.drawPath(mPath, mPaint);    //绘制路径
//        //绘制折线路径
//        mPath.reset();
//        //创建并实例化一个Path对象
//        mPath.moveTo(150, 100);        //设置起始点
//        mPath.lineTo(200, 45);        //设置第一段直线的结束点
//        mPath.lineTo(250, 100);        //设置第二段直线的结束点
//        mPath.lineTo(300, 80);        //设置第3段直线的结束点
//        canvas.drawPath(mPath, mPaint);    //绘制路径
//        //绘制三角形路径
//        mPath.reset();
//        mPath.moveTo(350, 80);    //设置起始点
//        mPath.lineTo(400, 30);    //设置第一条边的结束点，也是第二条边的起始点
//        mPath.lineTo(450, 80);    //设置第二条边的结束点，也是第3条边的起始点
//        mPath.close();            //闭合路径
//        canvas.drawPath(mPath, mPaint);    //绘制路径
//        //绘制绕路径的环形文字
//        String str = "风萧萧兮易水寒，壮士一去兮不复还";
//        mPath.reset();       //创建并实例化一个path对象
//        mPath.addCircle(550, 150, 300, Path.Direction.CW);        //添加顺时针的圆形路径
////        mPaint.setStyle(Paint.Style.FILL);//设置画笔的填充方式
//        canvas.drawTextOnPath(str, mPath, 0, 0, mPaint);    //绘制绕路径文字
        Paint paint=new Paint();	//创建一个画笔
        paint.setAntiAlias(true);	//设置使用抗锯齿功能
        paint.setColor(0xFFFF6600);	//设置画笔颜色
        paint.setTextSize(18);	//设置文字大小
        paint.setStyle(Paint.Style.STROKE);	//设置填充方式为描边
        //绘制圆形路径
        Path pathCircle=new Path();//创建并实例化一个path对象
        pathCircle.addCircle(70, 70, 40, Path.Direction.CCW);	//添加逆时针的圆形路径
        canvas.drawPath(pathCircle, paint);	//绘制路径
        //绘制折线路径
        Path pathLine=new Path();		//创建并实例化一个Path对象
        pathLine.moveTo(150, 100);		//设置起始点
        pathLine.lineTo(200, 45);		//设置第一段直线的结束点
        pathLine.lineTo(250, 100);		//设置第二段直线的结束点
        pathLine.lineTo(300, 80);		//设置第3段直线的结束点
        canvas.drawPath(pathLine, paint);	//绘制路径
        //绘制三角形路径
        Path pathTr=new Path();	//创建并实例化一个path对象
        pathTr.moveTo(350,80);	//设置起始点
        pathTr.lineTo(400, 30);	//设置第一条边的结束点，也是第二条边的起始点
        pathTr.lineTo(450, 80);	//设置第二条边的结束点，也是第3条边的起始点
        pathTr.close();			//闭合路径
        canvas.drawPath(pathTr, paint);	//绘制路径
        //绘制绕路径的环形文字
        String str="风萧萧兮易水寒，壮士一去兮不复还";
        Path path=new Path();		//创建并实例化一个path对象
        path.addCircle(550, 100, 48, Path.Direction.CW);		//添加顺时针的圆形路径
        paint.setStyle(Paint.Style.FILL);//设置画笔的填充方式
        canvas.drawTextOnPath(str, path,0, -18, paint);	//绘制绕路径文字

    }
}

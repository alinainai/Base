package trunk.doi.base.ui.activity.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：Mr.Lee on 2017-9-27 18:08
 * 邮箱：569932357@qq.com
 */

public class TestViewBezer extends View {

    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath=new Path();

    public TestViewBezer(Context context) {
        super(context);
        init();
    }

    public TestViewBezer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestViewBezer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TestViewBezer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){



        Paint paint=mPaint;
        paint.setAntiAlias(true);
        paint.setDither(true);
        mPaint.setColor(0xff000000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        Path path=mPath;
        path.moveTo(100,100);
        path.lineTo(400,400);

        path.quadTo(600,100,800,400);

        path.moveTo(400,800);
        path.cubicTo(500,600,700,1200,800,800);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
        canvas.drawPoint(600,100,mPaint);
        canvas.drawPoint(500,600,mPaint);
        canvas.drawPoint(700,1200,mPaint);

    }
}

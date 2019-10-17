package trunk.doi.base.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import trunk.doi.base.R;

public class TestView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;


    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

         mBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            mPaint=new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(20);
//            mPaint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0,0,100,0,mPaint);
        canvas.save();

        canvas.translate(200,0);
        canvas.rotate(45);
        canvas.drawLine(0,0,100,0,mPaint);
        canvas.drawBitmap(mBitmap,10,10,mPaint);
        canvas.restore();

        canvas.drawLine(0,0,100,0,mPaint);
        canvas.rotate(45);
        canvas.translate(200,0);

        canvas.drawLine(0,0,100,0,mPaint);
        canvas.drawBitmap(mBitmap,10,10,mPaint);
    }
}

package trunk.doi.base.ui.activity.test;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * 作者：Mr.Lee on 2017-9-6 14:35
 * 邮箱：569932357@qq.com
 */

public class MoveView extends View {

    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public MoveView(Context context) {
        super(context);
        ininView(context);

    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ininView(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ininView(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        // 判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }

    }

    private void ininView(Context context) {
        setBackgroundColor(Color.BLUE);
        // 初始化Scroller
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        switch (event.getAction()){
//
//            case MotionEvent.ACTION_DOWN:
//                lastX=(int) event.getRawX();
//                lastY=(int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int dx = (int) event.getRawX() - lastX;
//                int dy = (int) event.getRawY() - lastY;
//                layout(getLeft()+dx,getTop()+dy,getRight()+dx,getBottom()+dy);
//                lastX=(int) event.getRawX();
//                lastY=(int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_UP:
//
//
//                break;
//
//
//        }


        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
                View viewGroup = ((View) getParent());
                mScroller.startScroll(
                        viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY());
                invalidate();
                break;
        }

        return true;
    }
}


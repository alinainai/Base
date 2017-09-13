package trunk.doi.base.ui.activity.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 作者：Mr.Lee on 2017-9-11 09:30
 * 邮箱：569932357@qq.com
 */

@SuppressLint("AppCompatCustomView")
public class CustomImageView extends ImageView {

    private int lastX;
    private int lastY;

    private onImageMoveListener mLister;
    public interface onImageMoveListener{
        void onViewClick();
        void onViewClose();
    }
    public void setOnImageMoveListener(onImageMoveListener listener){
        this.mLister = listener;
    }


    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:


                break;
            case MotionEvent.ACTION_UP:
                //向下滑
                if(y-lastY>0){
                    if(Math.abs(lastX-x)<200 && y-lastY<200){
                        mLister.onViewClick();
                    }
                 //像上滑
                }else{
                    if(lastY-y>500){
                        mLister.onViewClose();
                    }else if(lastY-y<200 ){
                        if(Math.abs(lastX-x)<200)
                        mLister.onViewClick();
                    }
                }
                break;
        }

        return true;
    }

}

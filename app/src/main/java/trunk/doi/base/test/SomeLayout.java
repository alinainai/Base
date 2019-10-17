package trunk.doi.base.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SomeLayout extends ViewGroup {
    public SomeLayout(Context context) {
        super(context);
    }

    public SomeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SomeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            //字view的 MeasureSpec
            int childWidthSpec;
            int usedWidth=0;//已用宽度，此处虚拟
            LayoutParams lp = childView.getLayoutParams();
            int selfWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            switch (lp.width){
                case MATCH_PARENT:

                    if(selfWidthSpecMode==MeasureSpec.EXACTLY||selfWidthSpecMode==MeasureSpec.AT_MOST){
                        childWidthSpec=MeasureSpec.makeMeasureSpec(selfWidthSpecSize-usedWidth,MeasureSpec.EXACTLY);

                    }else {
                        childWidthSpec=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                    }
                    break;

                case WRAP_CONTENT:
                    if(selfWidthSpecMode==MeasureSpec.EXACTLY||selfWidthSpecMode==MeasureSpec.AT_MOST){
                        childWidthSpec=MeasureSpec.makeMeasureSpec(selfWidthSpecSize-usedWidth,MeasureSpec.AT_MOST);

                    }else {
                        childWidthSpec=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                    }
                    break;

                default:
                    childWidthSpec=MeasureSpec.makeMeasureSpec(lp.width,MeasureSpec.EXACTLY);
                    break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}

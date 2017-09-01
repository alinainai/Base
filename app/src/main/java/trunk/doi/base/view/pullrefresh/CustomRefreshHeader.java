package trunk.doi.base.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import trunk.doi.base.R;
import trunk.doi.base.ui.activity.test.ShootRefreshView;

/**
 * 作者：Mr.Lee on 2017-8-29 11:32
 * 邮箱：569932357@qq.com
 */

public class CustomRefreshHeader extends FrameLayout implements RefreshHeader{


    private TextView textView;
    private ShootRefreshView shoot_refresh_view;


    public CustomRefreshHeader(Context context) {
        this(context, null);
    }

    public CustomRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.header_custom, this);

        textView = (TextView) findViewById(R.id.text);
        shoot_refresh_view = (ShootRefreshView) findViewById(R.id.shoot_refresh_view);

    }

    @Override
    public void reset() {
        textView.setText(getResources().getText(R.string.qq_header_reset));
        shoot_refresh_view.reset();
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {

        textView.setText(getResources().getText(R.string.qq_header_refreshing));
        shoot_refresh_view.refreshing();

    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
        // 往上拉
        shoot_refresh_view.pullProgress(0, currentPos / refreshPos);

        if (currentPos < refreshPos && lastPos >= refreshPos) {
            if (isTouch && state == State.PULL) {
                textView.setText("下拉刷新");

            }
            // 往下拉
        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
            if (isTouch && state == State.PULL) {
                textView.setText("释放立即刷新");
            }
        }
    }

    @Override
    public void complete() {
        shoot_refresh_view.reset();
        textView.setText(getResources().getText(R.string.qq_header_completed));
    }

}

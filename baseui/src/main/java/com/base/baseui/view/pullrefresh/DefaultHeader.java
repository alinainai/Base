package com.base.baseui.view.pullrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.base.baseui.R;
import com.base.baseui.view.QMUILoadingView;


/**
 * 作者：Mr.Lee on 2017-8-29 11:32
 * 邮箱：569932357@qq.com
 */

public class DefaultHeader extends FrameLayout implements RefreshHeader{


    private TextView textView;
    private QMUILoadingView loading;


    public DefaultHeader(Context context) {
        this(context, null);
    }

    public DefaultHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.public_header_custom, this);
        textView =  findViewById(R.id.text);
        loading =  findViewById(R.id.loading);

    }

    public void setStyleColor(int resId) {
        textView.setTextColor(resId);
        loading.setColor(resId);
    }


    @Override
    public void reset() {
        textView.setText(getResources().getText(R.string.public_refresh_header_reset));

    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {

        textView.setText(getResources().getText(R.string.public_refresh_header_refreshing));

    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {
        // 往上拉

        if (currentPos < refreshPos && lastPos >= refreshPos) {
            if (isTouch && state == State.PULL) {
                textView.setText(getResources().getText(R.string.public_refresh_header_reset));
            }
            // 往下拉
        } else if (currentPos > refreshPos && lastPos <= refreshPos) {
            if (isTouch && state == State.PULL) {
                textView.setText(getResources().getText(R.string.public_refresh_header_pull_over));
            }
        }
    }

    @Override
    public void complete() {
        textView.setText(getResources().getText(R.string.public_refresh_header_load_success));
    }

}

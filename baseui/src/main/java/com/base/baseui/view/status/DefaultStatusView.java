package com.base.baseui.view.status;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.baseui.R;

import static com.base.baseui.view.status.Gloading.STATUS_EMPTY_DATA;
import static com.base.baseui.view.status.Gloading.STATUS_LOADING;
import static com.base.baseui.view.status.Gloading.STATUS_LOAD_FAILED;
import static com.base.baseui.view.status.Gloading.STATUS_LOAD_SUCCESS;


/**
 * simple loading status view for global usage
 *
 * @author billy.qi
 * @since 19/3/19 23:12
 */
@SuppressLint("ViewConstructor")
public class DefaultStatusView extends LinearLayout implements View.OnClickListener {

    public static final String HIDE_LOADING_STATUS_MSG = "SHOW_STATUS_MESSAGE";

    private final TextView mTextView;
    private final Runnable mRetryTask;
    private final ImageView mImageView;

    public DefaultStatusView(Context context, Runnable retryTask) {
        super(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.layout_status, this, true);
        mImageView = findViewById(R.id.img_status);
        mTextView = findViewById(R.id.base_empty_tv);
        setBackgroundResource(R.color.white);
        this.mRetryTask = retryTask;
    }



    public void setStatus(int status) {
        boolean show = true;
        Drawable anmimation;

        switch (status) {
            case STATUS_LOAD_SUCCESS://加载成功
                anmimation = mImageView.getDrawable();
                if (anmimation instanceof AnimationDrawable && ((AnimationDrawable) anmimation).isRunning()) {
                    ((AnimationDrawable) anmimation).stop();
                }
                show = false;
                break;
            case STATUS_LOADING://加载中
                mTextView.setText(R.string.str_status_view_loading);
                mImageView.setImageResource(R.drawable.status_loading_animation);
                anmimation = mImageView.getDrawable();
                if (anmimation instanceof AnimationDrawable && !((AnimationDrawable) anmimation).isRunning()) {
                    ((AnimationDrawable) anmimation).start();
                }

                break;
            case STATUS_LOAD_FAILED://加载失败
                anmimation = mImageView.getDrawable();
                if (anmimation instanceof AnimationDrawable && ((AnimationDrawable) anmimation).isRunning()) {
                    ((AnimationDrawable) anmimation).stop();
                }
                mImageView.setImageResource(R.mipmap.img_status_net_error);
                mTextView.setText(R.string.str_status_view_failed);
                setOnClickListener(this);
                break;
            case STATUS_EMPTY_DATA:
                anmimation = mImageView.getDrawable();
                if (anmimation instanceof AnimationDrawable && ((AnimationDrawable) anmimation).isRunning()) {
                    ((AnimationDrawable) anmimation).stop();
                }
                mImageView.setImageResource(R.mipmap.img_status_empty);
                setOnClickListener(this);
                mTextView.setText(R.string.str_status_view_empty);
                break;
        }

        setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mRetryTask != null) {
            mRetryTask.run();
        }
    }
}

package com.gas.test.widget.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.gas.test.R;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * desc: 倒计时pop布局
 * <p>
 * created by author ljx
 * Date  2020-03-04
 * email 569932357@qq.com
 * <p>
 * ================================================
 */
public class BottomTimeDownPromptBanner extends TimeDownPromptBanner {


    public BottomTimeDownPromptBanner(Context context) {
        super(context);
    }

    public BottomTimeDownPromptBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTimeDownPromptBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getDefaultBgRes() {
        return R.color.public_divider_color;
    }

    @Override
    protected int getDefaultTextColor() {
        return R.color.public_color_accent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_bottom_banner_timedown;
    }

    @Override
    protected int getShowAnimRes() {
        return R.anim.slide_in_from_bottom;
    }

    @Override
    protected int getHideAnimRes() {
        return  R.anim.slide_out_to_bottom;
    }
}

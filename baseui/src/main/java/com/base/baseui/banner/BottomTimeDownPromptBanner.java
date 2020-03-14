package com.base.baseui.banner;

import android.content.Context;
import android.util.AttributeSet;

import com.base.baseui.R;


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
        return R.layout.public_layout_bottom_banner_timedown;
    }

    @Override
    protected int getShowAnimRes() {
        return R.anim.public_slide_in_from_bottom;
    }

    @Override
    protected int getHideAnimRes() {
        return  R.anim.public_slide_out_to_bottom;
    }
}

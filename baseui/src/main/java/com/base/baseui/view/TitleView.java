package com.base.baseui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.base.baseui.R;


@SuppressWarnings("unused")
public class TitleView extends ConstraintLayout {
    private TextView tvBack;
    private TextView tvRight;
    private TextView tvTitle;
    private TextView tvClose;
    //背景颜色
    private View v_bg;
    //分割栏
    private View barLine;

    private String titleText;
    private int titleTextColor;

    private Drawable backIcon;

    private String rightText;
    private int rightTextColor;

    private int backColor;
    private boolean dividerHide;
    private boolean backHide;
    private boolean closeHide;


    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initAttrs(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleView);
        titleText = typedArray.getString(R.styleable.TitleView_titleText);
        titleTextColor = typedArray.getColor(R.styleable.TitleView_titleColor, ContextCompat.getColor(getContext(), R.color.black));

        backColor = typedArray.getColor(R.styleable.TitleView_backColor, ContextCompat.getColor(getContext(), R.color.white));

        rightText = typedArray.getString(R.styleable.TitleView_rightText);
        rightTextColor = typedArray.getColor(R.styleable.TitleView_rightTextColor, ContextCompat.getColor(getContext(), R.color.black));

        backIcon = typedArray.getDrawable(R.styleable.TitleView_backIcon);
        dividerHide = typedArray.getBoolean(R.styleable.TitleView_dividerHide, false);
        backHide = typedArray.getBoolean(R.styleable.TitleView_backHide, false);
        closeHide = typedArray.getBoolean(R.styleable.TitleView_closeHide, false);

        typedArray.recycle();


    }

    /**
     * 初始化文字标题栏
     */
    private void initView(AttributeSet attrs) {

        initAttrs(attrs);
        View view = View.inflate(getContext(), R.layout.baseui_layout_title, this);

        v_bg = view.findViewById(R.id.v_bg);

        tvTitle = view.findViewById(R.id.title_bar_name);
        tvBack = view.findViewById(R.id.title_bar_back);
        tvRight = view.findViewById(R.id.title_bar_right);
        tvClose = view.findViewById(R.id.title_bar_close);

        barLine = view.findViewById(R.id.v_divider_line);

        setTitleColor(titleTextColor);
        setTitleText(titleText);
        setBackgroundColor(backColor);
        setBarLineHide(dividerHide);
        setRightColor(rightTextColor);
        if (null == backIcon) {
            backIcon = ContextCompat.getDrawable(getContext(), R.mipmap.title_bar_back);
            setBackDrawable(backIcon);
        } else {
            setBackDrawable(backIcon);
        }
        setBackHide(backHide);
        setCloseHide(closeHide);

        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText);
        }

    }


    /**
     * 设置背景颜色
     *
     * @param color {@link android.graphics.Color}
     */
    public void setBackgroundColor(int color) {
        this.backColor = color;
        if (null != v_bg) {
            v_bg.setBackgroundColor(color);
        }
    }


    /**
     * 设置标题
     *
     * @param title String
     */
    public void setTitleText(String title) {
        this.titleText = title;
        if (null != tvTitle) {
            tvTitle.setText(title);
        }
    }

    /**
     * 获取标题
     */
    public String getTitleText() {
        return titleText;
    }

    /**
     * 设置标题字体颜色
     *
     * @param color int
     */
    public void setTitleColor(int color) {
        this.titleTextColor = color;
        tvTitle.setTextColor(color);
    }


    /**
     * 分割线隐藏
     *
     * @param isHide boolean true 隐藏
     */
    public void setBarLineHide(boolean isHide) {
        this.dividerHide = isHide;

        if (isHide) {
            if (barLine.getVisibility() == View.VISIBLE) {
                barLine.setVisibility(View.GONE);
            }
        } else {
            if (barLine.getVisibility() == View.GONE) {
                barLine.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 返回键隐藏
     *
     * @param isHide true 隐藏
     */
    public void setBackHide(boolean isHide) {
        this.backHide = isHide;
        if (null != tvBack) {
            if (isHide) {
                if (tvBack.getVisibility() == View.VISIBLE) {
                    tvBack.setVisibility(View.GONE);
                }
            } else {
                if (tvBack.getVisibility() == View.GONE) {
                    tvBack.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 关闭键隐藏
     *
     * @param isHide true 隐藏
     */
    public void setCloseHide(boolean isHide) {
        this.closeHide = isHide;
        if (null != tvClose) {
            if (isHide) {
                if (tvClose.getVisibility() == View.VISIBLE) {
                    tvClose.setVisibility(View.GONE);
                }
            } else {
                if (tvClose.getVisibility() == View.GONE) {
                    tvClose.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 设置返回键图标
     *
     * @param drawable Drawable
     */
    public void setBackDrawable(Drawable drawable) {
        this.backIcon = drawable;
        if (tvBack != null) {
            tvBack.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    /**
     * 设置返回键监听事件
     */
    public void setOnBackListener(View.OnClickListener clickListener) {

        if (null != tvBack) {
            if (clickListener != null) {
                tvBack.setOnClickListener(clickListener);
            }
        }
    }

    /**
     * 设置右边功能颜色
     */
    public void setRightColor(int color) {
        this.rightTextColor = color;
        if (null != tvRight) {
            tvRight.setTextColor(color);
        }
    }

    /**
     * 设置右边标题文字
     */
    public void setRightText(String title) {
        this.rightText = title;
        if (null != tvRight) {
            tvRight.setText(title);
        }
    }

    /**
     * 设置右边功能监听事件
     */
    public void setOnRightListener(View.OnClickListener clickListener) {
        if (null != tvRight) {
            if (clickListener != null) {
                tvRight.setOnClickListener(clickListener);
            }
        }

    }

    /**
     * 获取 标题右按钮布局
     *
     * @return View
     */
    public View getRightView() {
        if (null != tvRight) {
            return tvRight;
        }
        return null;
    }


}

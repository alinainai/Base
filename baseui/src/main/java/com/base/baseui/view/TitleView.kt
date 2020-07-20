package com.base.baseui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.base.baseui.R

/**
 * 自定义TitleView
 */
class TitleView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var tvBack: TextView? = null
    private var tvRight: TextView? = null
    private var tvTitle: TextView? = null
    private var tvClose: TextView? = null

    //背景颜色
    private var v_bg: View? = null

    //分割栏
    private var barLine: View? = null
    private var titleText: String? = null
    private var titleTextColor = 0
    private var backIcon: Drawable? = null
    private var rightText: String? = null
    private var rightTextColor = 0
    private var backColor = 0
    private var dividerHide = false
    private var backHide = false
    private var closeHide = false
    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
        titleText = typedArray.getString(R.styleable.TitleView_titleText)
        titleTextColor = typedArray.getColor(R.styleable.TitleView_titleColor, ContextCompat.getColor(context, R.color.public_black))
        backColor = typedArray.getColor(R.styleable.TitleView_backColor, ContextCompat.getColor(context, R.color.public_white))
        rightText = typedArray.getString(R.styleable.TitleView_rightText)
        rightTextColor = typedArray.getColor(R.styleable.TitleView_rightTextColor, ContextCompat.getColor(context, R.color.public_black))
        backIcon = typedArray.getDrawable(R.styleable.TitleView_backIcon)
        dividerHide = typedArray.getBoolean(R.styleable.TitleView_dividerHide, false)
        backHide = typedArray.getBoolean(R.styleable.TitleView_backHide, false)
        closeHide = typedArray.getBoolean(R.styleable.TitleView_closeHide, false)
        typedArray.recycle()
    }

    /**
     * 初始化文字标题栏
     */
    private fun initView(attrs: AttributeSet?) {
        initAttrs(attrs)
        val view = View.inflate(context, R.layout.public_layout_title, this)
        v_bg = view.findViewById(R.id.v_bg)
        tvTitle = view.findViewById(R.id.title_bar_name)
        tvBack = view.findViewById(R.id.title_bar_back)
        tvRight = view.findViewById(R.id.title_bar_right)
        tvClose = view.findViewById(R.id.title_bar_close)
        barLine = view.findViewById(R.id.v_divider_line)
        setTitleColor(titleTextColor)
        setTitleText(titleText)
        setBackgroundColor(backColor)
        setBarLineHide(dividerHide)
        setRightColor(rightTextColor)
        if (null == backIcon) {
            backIcon = ContextCompat.getDrawable(context, R.mipmap.lib_title_bar_back)
            setBackDrawable(backIcon)
        } else {
            setBackDrawable(backIcon)
        }
        setBackHide(backHide)
        setCloseHide(closeHide)
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText)
        }
    }

    /**
     * 设置背景颜色
     *
     * @param color [android.graphics.Color]
     */
    override fun setBackgroundColor(color: Int) {
        backColor = color
        if (null != v_bg) {
            v_bg!!.setBackgroundColor(color)
        }
    }

    /**
     * 设置标题
     *
     * @param title String
     */
    fun setTitleText(title: String?) {
        titleText = title
        if (null != tvTitle) {
            if (!TextUtils.isEmpty(title)) {
                tvTitle!!.text = title
            }
        }
    }

    /**
     * 获取标题
     */
    fun getTitleText(): String? {
        return titleText
    }

    /**
     * 设置标题字体颜色
     *
     * @param color int
     */
    fun setTitleColor(color: Int) {
        titleTextColor = color
        tvTitle!!.setTextColor(color)
    }

    /**
     * 分割线隐藏
     *
     * @param isHide boolean true 隐藏
     */
    fun setBarLineHide(isHide: Boolean) {
        dividerHide = isHide
        if (isHide) {
            if (barLine!!.visibility == View.VISIBLE) {
                barLine!!.visibility = View.GONE
            }
        } else {
            if (barLine!!.visibility == View.GONE) {
                barLine!!.visibility = View.VISIBLE
            }
        }
    }

    /**
     * 返回键隐藏
     *
     * @param isHide true 隐藏
     */
    fun setBackHide(isHide: Boolean) {
        backHide = isHide
        if (null != tvBack) {
            if (isHide) {
                if (tvBack!!.visibility == View.VISIBLE) {
                    tvBack!!.visibility = View.GONE
                }
            } else {
                if (tvBack!!.visibility == View.GONE) {
                    tvBack!!.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * 关闭键隐藏
     *
     * @param isHide true 隐藏
     */
    fun setCloseHide(isHide: Boolean) {
        closeHide = isHide
        if (null != tvClose) {
            if (isHide) {
                if (tvClose!!.visibility == View.VISIBLE) {
                    tvClose!!.visibility = View.GONE
                }
            } else {
                if (tvClose!!.visibility == View.GONE) {
                    tvClose!!.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * 设置返回键图标
     *
     * @param drawable Drawable
     */
    fun setBackDrawable(drawable: Drawable?) {
        backIcon = drawable
        if (tvBack != null) {
            tvBack!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }
    }

    /**
     * 设置返回键监听事件
     */
    fun setOnBackListener(clickListener: OnClickListener?) {
        if (null != tvBack) {
            if (clickListener != null) {
                tvBack!!.setOnClickListener(clickListener)
            }
        }
    }

    /**
     * 设置右边功能颜色
     */
    fun setRightColor(color: Int) {
        rightTextColor = color
        if (null != tvRight) {
            tvRight!!.setTextColor(color)
        }
    }

    /**
     * 设置右边标题文字
     */
    fun setRightText(title: String?) {
        rightText = title
        if (null != tvRight) {
            tvRight!!.text = title
        }
    }

    /**
     * 设置右边功能监听事件
     */
    fun setOnRightListener(clickListener: OnClickListener?) {
        if (null != tvRight) {
            if (clickListener != null) {
                tvRight!!.setOnClickListener(clickListener)
            }
        }
    }

    /**
     * 获取 标题右按钮布局
     *
     * @return View
     */
    val rightView: View?
        get() = if (null != tvRight) {
            tvRight
        } else null

    init {
        initView(attrs)
    }
}
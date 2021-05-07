package com.gas.zhihu.view

import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText

class CleanEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.editTextStyle) : AppCompatEditText(context, attrs, defStyleAttr) {
    private val WHITE_SPACE = " "
    private var mClearDrawable: Drawable? = null
    private var mInputType = -1
    private var mDeleteClicklistener: OnDeleteClickListener? = null
    private val shouldStopChange = false
    private fun init() {
        mClearDrawable = resources.getDrawable(com.gas.zhihu.R.mipmap.zhihu_clear_normal)
    }

    fun setmInputType(mInputType: Int) {
        this.mInputType = mInputType
    }

    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        setClearIconVisible(hasFocus() && length() > 0)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        setClearIconVisible(focused && length() > 0)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val drawable = compoundDrawables[DRAWABLE_RIGHT]
                if (drawable != null && event.x <= width - paddingRight && event.x >= width - paddingRight - drawable.bounds.width()) {
                    setText("")
                    if (mDeleteClicklistener != null) mDeleteClicklistener!!.onDeleteClick()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun setClearIconVisible(visible: Boolean) {
        setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[DRAWABLE_LEFT], compoundDrawables[DRAWABLE_TOP], if (visible) mClearDrawable else null, compoundDrawables[DRAWABLE_BOTTOM])
    }

    val noSpaceText: String
        get() = if (text != null) text.toString().trim { it <= ' ' }.replace(WHITE_SPACE.toRegex(), "") else ""

    fun setDeleteClickListener(listener: OnDeleteClickListener?) {
        mDeleteClicklistener = listener
    }

    interface OnDeleteClickListener {
        fun onDeleteClick()
    }

    companion object {
        private const val DRAWABLE_LEFT = 0
        private const val DRAWABLE_TOP = 1
        private const val DRAWABLE_RIGHT = 2
        private const val DRAWABLE_BOTTOM = 3
    }

    init {
        val a = context.obtainStyledAttributes(attrs, com.gas.zhihu.R.styleable.CleanEditText)
        if (a.getBoolean(com.gas.zhihu.R.styleable.CleanEditText_isDinOtf, false)) {
            try {
                val newFont = Typeface.createFromAsset(context.assets, "fonts/din_alternate_bold.ttf")
                typeface = newFont
            } catch (e: Exception) {
            }
        }
        a.recycle()
        init()
    }
}
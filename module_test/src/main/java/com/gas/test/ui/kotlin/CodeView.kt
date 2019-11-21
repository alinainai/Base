package com.gas.test.ui.kotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.gas.test.R
import java.util.*

class CodeView constructor(context: Context, attrs: AttributeSet?) : AppCompatTextView(context,attrs) {


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var codeList = arrayOf(
            "李佳星",
            "韩桂敏",
            "李沐安"
    )

    constructor(context: Context) : this(context, null)
    init {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        gravity = Gravity.CENTER
        setBackgroundColor(context.resources.getColor(R.color.public_colorAccent))
        setTextColor(context.resources.getColor(R.color.public_color_primary_dark))

        mPaint.style = Paint.Style.STROKE
        mPaint.color = context.resources.getColor(R.color.public_colorPrimary)
        mPaint.strokeWidth = 6f.dp2px()

        updateCode()
    }

    fun updateCode() {

        val random = Random().nextInt(codeList.size)
        val code = codeList[random]
        text = code

    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
        super.onDraw(canvas)
    }

}
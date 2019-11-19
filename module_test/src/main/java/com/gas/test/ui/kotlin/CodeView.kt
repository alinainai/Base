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

class CodeView : AppCompatTextView {


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var codeList = arrayOf(
            "李佳星",
            "韩桂敏",
            "李沐安"
    )

    //     int类型的数组，注意这里用的是 intArrayOf
//    private var numList= intArrayOf(
//           1,2,3,4
//    )

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        gravity = Gravity.CENTER
        setBackgroundColor(context.resources.getColor(R.color.public_colorAccent))
        setTextColor(context.resources.getColor(R.color.public_color_primary_dark))

        mPaint.style = Paint.Style.STROKE
        mPaint.color = context.resources.getColor(R.color.public_colorPrimary)
        mPaint.strokeWidth = dp2px(5f)

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
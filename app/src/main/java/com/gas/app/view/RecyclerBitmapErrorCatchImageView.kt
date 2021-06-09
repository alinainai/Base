package com.gas.app.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.gas.app.R

class RecyclerBitmapErrorCatchImageView @JvmOverloads  constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        try {
            super.onDraw(canvas)
        } catch (e: RuntimeException) {

            setImageResource(R.mipmap.baseui_adapter_network_fail)
        }
    }
}


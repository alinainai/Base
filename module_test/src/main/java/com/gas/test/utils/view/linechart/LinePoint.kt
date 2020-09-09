package com.gas.test.utils.view.linechart

import android.content.Context
import android.graphics.Paint
import android.util.TypedValue

/**
 * The Class LinePoint.
 */
class LinePoint {
    /**
     *
     * @return horizontal coordinate
     */
    var x = 0f

    /**
     * Gets the vertical coordinate.
     *
     * @return vertical coordinate
     */
    var y = 0f

    /**
     * Checks if point visible.
     *
     * @return true, if visible
     */
    var isVisible = false

    /**
     * @return stroke [Paint]
     *
     * @see .setStrokePaint
     */
    var strokePaint = Paint()

    /**
     * @return fill [Paint]
     *
     * @see .setFillPaint
     */
    var fillPaint = Paint()

    /**
     * Gets the text [Paint].
     *
     * @return text [Paint]
     *
     * @see .setTextPaint
     */
    var textPaint = Paint()

    /**
     * Gets the type.
     *
     * @return the type
     */
    var type = Type.CIRCLE

    /**
     *
     * @return radius of the point for visualisation in pixels
     */
    var radius = 5f

    /**
     * @return text for the point
     */
    var text = ""

    /**
     * Checks if is text visible.
     *
     * @return true, if is text visible
     */
    var isTextVisible = false

    /**
     *
     * @return [TextAlign] for the text
     */
    var textAlign = TextAlign.HORIZONTAL_CENTER or TextAlign.TOP

    /**
     * Type of Point visualisation in [LineChartView].
     */
    enum class Type {
        /** Circle.  */
        CIRCLE,

        /** Square.  */
        SQUARE,

        /** Triangle.  */
        TRIANGLE
    }

    /**
     * Instantiates a new line point.
     *
     * @param context context
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    constructor(context: Context, x: Float, y: Float) {
        init(context)
        setPosition(x, y)
    }

    /**
     * Instantiates a new line point.
     * @param context the context
     */
    constructor(context: Context) {
        init(context)
    }

    /**
     * Sets the position of the point
     *
     * @param x
     * horizontal coordinate
     * @param y
     * vertical coordinate
     * @return this
     */
    fun setPosition(x: Float, y: Float): LinePoint {
        setX(x)
        setY(y)
        return this
    }

    private fun init(context: Context) {
        strokePaint.color = -0xcc4a1b
        strokePaint.style = Paint.Style.STROKE
        strokePaint.isAntiAlias = true
        strokePaint.strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f,
                context.resources.displayMetrics)
        fillPaint.color = -0x1
        textPaint.color = -0x33bbbbbc
        textPaint.isAntiAlias = true
        textPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10f,
                context.resources.displayMetrics)
    }

    /**
     * Sets horizontal coordinate.
     *
     * @param x
     * horizontal coordinate
     * @return this
     */
    fun setX(x: Float): LinePoint {
        this.x = x
        return this
    }

    /**
     * Sets the vertical coordinate.
     *
     * @param y
     * vertical coordinate
     * @return this
     */
    fun setY(y: Float): LinePoint {
        this.y = y
        return this
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    override fun toString(): String {
        return "x= $x, y= $y"
    }

    /**
     * Sets point visibility.
     *
     * @param isVisible
     * true if visible, false otherwise
     * @return this
     */
    fun setVisible(isVisible: Boolean): LinePoint {
        this.isVisible = isVisible
        return this
    }

    /**
     * Sets stroke paint.
     *
     * @param strokePaint
     * stroke [Paint]
     * @return this
     *
     * @see .getStrokePaint
     */
    fun setStrokePaint(strokePaint: Paint): LinePoint {
        this.strokePaint = strokePaint
        return this
    }

    /**
     * Sets [Paint] to fill center of point.
     *
     * @param fillPaint
     * fill [Paint]
     * @return this
     */
    fun setFillPaint(fillPaint: Paint): LinePoint {
        this.fillPaint = fillPaint
        return this
    }

    /**
     * Sets the text [Paint] for text near point
     *
     * @param textPaint
     * text [Paint]
     * @return this
     *
     * @see .getTextPaint
     */
    fun setTextPaint(textPaint: Paint): LinePoint {
        this.textPaint = textPaint
        return this
    }

    /**
     * Sets [Type] for point visualization.
     *
     * @param type
     * type
     * @return this
     */
    fun setType(type: Type): LinePoint {
        this.type = type
        return this
    }

    /**
     * Sets the radius for point visualisation in pixels.
     *
     * @param radius
     * radius in pixels
     * @return this
     */
    fun setRadius(radius: Float): LinePoint {
        this.radius = radius
        return this
    }

    /**
     * Sets text for point which will be drawn if [.isTextVisible].
     *
     * @param text
     * the text
     * @return this
     */
    fun setText(text: String): LinePoint {
        this.text = text
        return this
    }

    /**
     * Sets the text visible.
     *
     * @param isTextVisible
     * is text visible
     * @return this
     *
     * @see .isTextVisible
     */
    fun setTextVisible(isTextVisible: Boolean): LinePoint {
        this.isTextVisible = isTextVisible
        return this
    }

    /**
     * Sets [TextAlign] for the text.
     *
     * @param textAlign
     * bitmask from [TextAlign]
     * @return this
     */
    fun setTextAlign(textAlign: Int): LinePoint {
        this.textAlign = textAlign
        return this
    }
}
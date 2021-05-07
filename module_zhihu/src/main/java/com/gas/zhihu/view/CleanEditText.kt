package com.gas.zhihu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.appcompat.R.attr;
import androidx.appcompat.widget.AppCompatEditText;

import com.gas.zhihu.R;


/**
 * ================================================
 * desc:
 *
 * created by author ljx
 * date  2020/3/28
 * email 569932357@qq.com
 *
 * ================================================
 */

public class CleanEditText extends AppCompatEditText {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private final String WHITE_SPACE = " ";
    private Drawable mClearDrawable;
    private int mInputType = -1;
    private OnDeleteClickListener mDeleteClicklistener;
    private boolean shouldStopChange = false;

    public CleanEditText(Context context) {
        this(context, null);
    }

    public CleanEditText(Context context, AttributeSet attrs) {
        this(context, attrs, attr.editTextStyle);
    }

    public CleanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CleanEditText);
        if (a.getBoolean(R.styleable.CleanEditText_isDinOtf, false)) {
            try {
                Typeface newFont = Typeface.createFromAsset(context.getAssets(), "fonts/din_alternate_bold.ttf");
                setTypeface(newFont);
            } catch (Exception e) {

            }
        }
        a.recycle();
        init();
    }

    private void init() {
        mClearDrawable = getResources().getDrawable(R.mipmap.zhihu_clear_normal);
    }

    public void setmInputType(int mInputType) {
        this.mInputType = mInputType;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                    if (mDeleteClicklistener != null)
                        mDeleteClicklistener.onDeleteClick();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP]
                , visible ? mClearDrawable : null, getCompoundDrawables()[DRAWABLE_BOTTOM]);
    }

    public String getNoSpaceText() {
        return (getText() != null ? getText().toString().trim().replaceAll(WHITE_SPACE, "") : "");
    }

    public void setDeleteClickListener(OnDeleteClickListener listener) {
        mDeleteClicklistener = listener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick();
    }
}

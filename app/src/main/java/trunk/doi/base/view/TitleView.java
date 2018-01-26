package trunk.doi.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import trunk.doi.base.R;


/**
 * @author WangLianJun created on 2016/11/11 15:31
 * @name 标题栏：文字标题栏，搜索标题栏
 * @mail qq695301501
 * @desc 通过set设置按钮Icon及文字
 * @tools
 */
public class TitleView extends LinearLayout{
    /**
     * 文字标题栏
     */
    private TextView v1TvLeft, v1TvRight,viewTxtName;
    private RelativeLayout ry_title_content;
    private View barLine;

    private String titleText;
    private int titleTextColor;
    private float titltTextSize = 16;

    private Drawable backIcon;

    private String rightText;
    private int rightTextColor;
    private float rightTextSize = 14;

    private int backColor;
    private boolean rightHide;
    private boolean dividerHide;


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

    private void initAttrs(AttributeSet attrs){

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleView);
        titleText = typedArray.getString(R.styleable.TitleView_titleText);
        titltTextSize = typedArray.getDimension(R.styleable.TitleView_titleTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics()));
        titleTextColor = typedArray.getColor(R.styleable.TitleView_titleColor, ContextCompat.getColor(getContext(),R.color.black) );

        backColor = typedArray.getColor(R.styleable.TitleView_backColor, ContextCompat.getColor(getContext(),R.color.white) );

        rightText = typedArray.getString(R.styleable.TitleView_rightText);
        rightTextColor = typedArray.getColor(R.styleable.TitleView_rightTextColor, ContextCompat.getColor(getContext(),R.color.black) );
        rightTextSize = typedArray.getDimension(R.styleable.TitleView_rightTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics()));

        backIcon = typedArray.getDrawable(R.styleable.TitleView_backIcon);
        rightHide = typedArray.getBoolean(R.styleable.TitleView_rightHide,true);
        dividerHide = typedArray.getBoolean(R.styleable.TitleView_dividerHide,false);

        typedArray.recycle();


    }

    /**
     * 初始化文字标题栏
     */
    private void initView(AttributeSet attrs) {

        initAttrs(attrs);
        setOrientation(LinearLayout.VERTICAL);
        View view = View.inflate(getContext(), R.layout.common_title_bar, this);
        ry_title_content = view.findViewById(R.id.ry_title_content);
        viewTxtName = view. findViewById(R.id.title_bar_name);
        v1TvLeft = view.findViewById(R.id.title_bar_back);
        v1TvRight = view.findViewById(R.id.title_bar_edt);
        barLine = view.findViewById(R.id.v_divider_line);

        setTitleColor(titleTextColor);
        setTitleText(titleText);
        setBackgroundColor(backColor);
        setTitleSize(titltTextSize);
        setBarLineHide(dividerHide);
        setRightHide(rightHide);
        setRightColor(rightTextColor);
        setRightSize(rightTextSize);
        if(null==backIcon){
            backIcon=ContextCompat.getDrawable(getContext(),R.mipmap.titlebar_back_icon);
            setBackDrawable(backIcon);
        }
        setRightText(rightText);

    }




    /**
     *  设置背景颜色
     * @param color
     */
    public void setBackgroundColor(int color) {
        ry_title_content.setBackgroundColor(color);
    }

    /**
     * 设置标题
     * @param resId
     */
    public void setTitleText(int resId) {
        viewTxtName.setText(resId);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitleText(CharSequence title) {
        viewTxtName.setText(title);
    }

    /**
     * 设置标题字体颜色
     * @param color
     */
    public void setTitleColor(int color) {
        viewTxtName.setTextColor(color);
    }
    /**
     * 设置标题字体颜色
     * @param size
     */
    public void setTitleSize(float size) {
        viewTxtName.setTextSize(size);
    }

    /**
     * 分割线隐藏
     * @param isHide
     */
    public void setBarLineHide(boolean isHide) {
        if(isHide){
            if(barLine.getVisibility()==View.VISIBLE){
                barLine.setVisibility(View.GONE);
            }
        }else{
            if(barLine.getVisibility()==View.GONE){
                barLine.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置返回键图标
     * @param drawable
     */
    public void setBackDrawable(Drawable drawable) {
        v1TvLeft.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    /**
     * 设置返回键监听事件
     */
    public void setBackListener(View.OnClickListener clickListener) {
        if(clickListener!=null){
            v1TvLeft.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置返回键隐藏
     */
    public void setBackHide() {
        if(v1TvLeft.getVisibility()==View.VISIBLE){
            v1TvLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 默认是隐藏的
     * 设置右边功能键隐藏
     */
    public void setRightHide(boolean isHide) {
        if(isHide){
            if(v1TvRight.getVisibility()==View.VISIBLE){
                v1TvRight.setVisibility(View.GONE);
            }
        }else{
            if(v1TvRight.getVisibility()==View.GONE){
                v1TvRight.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 设置右边功能颜色
     */
    public void setRightColor(int color) {
        v1TvRight.setTextColor(color);

    }
    /**
     * 设置右边标题文字
     */
    public void setRightText(CharSequence title) {
        v1TvRight.setText(title);
    }
    /**
     * 设置右边功能颜色
     */
    public void setRightSize(float size) {
        v1TvRight.setTextSize(size);
    }

    /**
     * 设置右边功能监听事件
     */
    public void setRightListener(View.OnClickListener clickListener) {
        if(clickListener!=null){
            v1TvRight.setOnClickListener(clickListener);
        }
    }






}

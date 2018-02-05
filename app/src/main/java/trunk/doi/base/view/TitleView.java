package trunk.doi.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import trunk.doi.base.R;


/**
 *
 */
public class TitleView extends FrameLayout{
    /**
     * 文字标题栏
     */
    private TextView v1TvLeft, v1TvRight,viewTxtName;
    private RelativeLayout ry_title_content;
    private View barLine;

    private String titleText;
    private int titleTextColor;

    private Drawable backIcon;

    private String rightText;
    private int rightTextColor;

    private int backColor;
    private boolean dividerHide;
    private boolean backHide;


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
        titleTextColor = typedArray.getColor(R.styleable.TitleView_titleColor, ContextCompat.getColor(getContext(),R.color.black) );

        backColor = typedArray.getColor(R.styleable.TitleView_backColor, ContextCompat.getColor(getContext(),R.color.white) );

        rightText = typedArray.getString(R.styleable.TitleView_rightText);
        rightTextColor = typedArray.getColor(R.styleable.TitleView_rightTextColor, ContextCompat.getColor(getContext(),R.color.black) );

        backIcon = typedArray.getDrawable(R.styleable.TitleView_backIcon);
        dividerHide = typedArray.getBoolean(R.styleable.TitleView_dividerHide,false);
        backHide = typedArray.getBoolean(R.styleable.TitleView_backHide,false);

        typedArray.recycle();


    }

    /**
     * 初始化文字标题栏
     */
    private void initView(AttributeSet attrs) {

        initAttrs(attrs);
        View view = View.inflate(getContext(), R.layout.common_title_bar, this);
        ry_title_content = view.findViewById(R.id.ry_title_content);
        viewTxtName = view. findViewById(R.id.title_bar_name);
        v1TvLeft = view.findViewById(R.id.title_bar_back);
        v1TvRight = view.findViewById(R.id.title_bar_edt);
        barLine = view.findViewById(R.id.v_divider_line);
        setTitleColor(titleTextColor);
        setTitleText(titleText);
        setBackgroundColor(backColor);
        setBarLineHide(dividerHide);
        setRightColor(rightTextColor);
        if(null==backIcon){
            backIcon=ContextCompat.getDrawable(getContext(),R.mipmap.titlebar_back_icon);
            setBackDrawable(backIcon);
        }else{
            setBackDrawable(backIcon);
        }
        setBackHide(backHide);
        if(!TextUtils.isEmpty(rightText)){
            setRightText(rightText);
        }

    }




    /**
     *  设置背景颜色
     * @param color
     */
    public void setBackgroundColor(int color) {
        this.backColor=color;
        if(null!=ry_title_content){
            ry_title_content.setBackgroundColor(color);
        }
    }


    /**
     * 设置标题
     * @param title
     */
    public void setTitleText(String title) {
        this.titleText=title;
        if(null!=viewTxtName){
            viewTxtName.setText(title);
        };
    }
    /**
     * 设置标题
     */
    public String getTitleText() {
        return titleText;
    }

    /**
     * 设置标题字体颜色
     * @param color
     */
    public void setTitleColor(int color) {
        this.titleTextColor=color;
        if(null!=viewTxtName){
            viewTxtName.setTextColor(color);
        };
    }


    /**
     * 分割线隐藏
     * @param isHide
     */
    public void setBarLineHide(boolean isHide) {
        this.dividerHide=isHide;
        if(null!=barLine){
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
    }

    /**
     * 分割线隐藏
     * @param isHide
     */
    public void setBackHide(boolean isHide) {
        this.backHide=isHide;
        if(null!=v1TvLeft){
            if(isHide){
                if(v1TvLeft.getVisibility()==View.VISIBLE){
                    v1TvLeft.setVisibility(View.GONE);
                }
            }else{
                if(v1TvLeft.getVisibility()==View.GONE){
                    v1TvLeft.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 设置返回键图标
     * @param drawable
     */
    public void setBackDrawable(Drawable drawable) {
        this.backIcon=drawable;
        if(v1TvLeft!=null){
            v1TvLeft.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    /**
     * 设置返回键监听事件
     */
    public void setOnBackListener(View.OnClickListener clickListener) {

        if(null!=v1TvLeft){
            if(clickListener!=null){
                v1TvLeft.setOnClickListener(clickListener);
            }
        }
    }

    /**
     * 设置右边功能颜色
     */
    public void setRightColor(int color) {
        this.rightTextColor=color;
        if(null!=v1TvRight){
            v1TvRight.setTextColor(color);
        }
    }
    /**
     * 设置右边标题文字
     */
    public void setRightText(String title) {
        this.rightText=title;
        if(null!=v1TvRight){
            v1TvRight.setText(title);
        }
    }

    /**
     * 设置右边功能监听事件
     */
    public void setRightListener(View.OnClickListener clickListener) {
        if(null!=v1TvRight){
            if(clickListener!=null){
                v1TvRight.setOnClickListener(clickListener);
            }
        }

    }

}
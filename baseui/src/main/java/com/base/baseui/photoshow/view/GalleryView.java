package com.base.baseui.photoshow.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Message;

import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.base.baseui.R;
import com.base.baseui.photoshow.adapter.PhotoAdapter;
import com.base.baseui.photoshow.model.IPhotoProvider;
import com.base.baseui.photoshow.model.PhotoParameter;
import com.base.baseui.photoshow.util.GalleryScreenUtil;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by bjhl on 2018/3/26.
 */

public class GalleryView extends RelativeLayout {

    private static final int DEFAULT_ANIM_DURATION = 300; // 毫秒

    // 用于缩放的ImageView
    private ImageView mScaleImageView = null;
    // 阴影背景
    private View maskView;
    // 屏幕的宽高
    private RectF mScreenRect = null;
    //viewpager滑到的位置
    private int position;
    private int animDuration;

    private ViewPager viewPager;

    private List<WrapPhotoView> viewList;
    private List<IPhotoProvider> photoList;
    private PhotoParameter firstClickItemParameterModel;
    private PhotoAdapter adapter;






    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GalleryView);
            animDuration = typedArray.getInt(R.styleable.GalleryView_animDuration, DEFAULT_ANIM_DURATION);
            typedArray.recycle();
        }
        else {
            animDuration = DEFAULT_ANIM_DURATION;
        }

        setFocusable(true);
        setFocusableInTouchMode(true);
        initView();
        // 拦截单击事件，防止其他view被点击
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && getVisibility() == VISIBLE) {
            calculateScaleAndStartZoomOutAnim();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param index             想要展示的图片的索引值
     * @param photoList         图片集合（URL、Drawable、Bitmap）
     * @param clickImageView    点击的第一个图片
     */
    public void showPhotoGallery(int index, List<IPhotoProvider> photoList, ImageView clickImageView) {
        PhotoParameter photoParameter = new PhotoParameter();
        //图片地址
        photoParameter.photoObj = photoList.get(index).providePhoto();
        //图片在list中的索引
        photoParameter.index = index;
        int[] locationOnScreen = new int[2];
        //图片位置参数
        clickImageView.getLocationOnScreen(locationOnScreen);
        photoParameter.locOnScreen = locationOnScreen;
        //图片的宽高
        int width = clickImageView.getDrawable().getBounds().width();
        int height = clickImageView.getDrawable().getBounds().height();
        photoParameter.imageWidth = clickImageView.getWidth();
        photoParameter.imageHeight = clickImageView.getHeight();
        photoParameter.photoHeight = height;
        photoParameter.photoWidth = width;
        //scaleType
        photoParameter.scaleType = clickImageView.getScaleType();
        //将第一个点击的图片参数连同整个图片列表传入
        this.setVisibility(View.VISIBLE);
        post(new Runnable() {
            @Override
            public void run() {
                requestFocus();
            }
        });
        setGalleryPhotoList(photoList, photoParameter);
    }

    /**
     * 设置缩放时动画持续时间
     * @param duration 毫秒
     */
    public void setAnimDuration(int duration) {
        animDuration = duration;
    }




    private void setGalleryPhotoList(List<IPhotoProvider> list, PhotoParameter parameterModel) {
//        boolean isFocus = requestFocus();
        if (list != null && parameterModel != null) {
            if (photoList != null && photoList.size() != 0) {
                photoList.clear();
            } else {
                photoList = new ArrayList<>();
            }
            photoList.addAll(list);
            //获取第一个被点击的图片的具体参数
            firstClickItemParameterModel = parameterModel;
            initData();
            //处理放大动画
            handleZoomAnimation();
        }
    }

    private void handleZoomAnimation() {
        // 屏幕的宽高
        this.mScreenRect = GalleryScreenUtil.getDisplayPixes(getContext());
        //将被缩放的图片放在一个单独的ImageView上进行单独的动画处理。
        Glide.with(getContext()).load(firstClickItemParameterModel.photoObj).into(mScaleImageView);
        //开启动画
        mScaleImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //开始放大操作
                calculateScaleAndStartZoomInAnim(firstClickItemParameterModel);
                //
                mScaleImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    /**
     * 初始化视图
     */
    private void initView() {
        View view = View.inflate(getContext(), R.layout.public_gallery_view_main, this);
        maskView = view.findViewById(R.id.gallery_view_main_mask_View);
        mScaleImageView = (ImageView) view.findViewById(R.id.gallery_view_main_scale_imageView);
        viewPager = (ViewPager) view.findViewById(R.id.gallery_view_main_viewpager);
    }

    /**
     * 初始化数据，设置事件监听
     */
    private void initData() {
        if (viewList != null && viewList.size() != 0) {
            viewList.clear();
        } else {
            viewList = new ArrayList<>();
        }
        for (int i = 0; i < photoList.size(); i++) {
            WrapPhotoView galleryPhoto = new WrapPhotoView(getContext(), photoList.get(i));
            viewList.add(galleryPhoto);
            galleryPhoto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //隐藏当前viewpager
                    calculateScaleAndStartZoomOutAnim();
                }
            });
        }
        adapter = new PhotoAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                GalleryView.this.position = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(firstClickItemParameterModel.index);


    }

    private void showOtherViews() {
        mScaleImageView.setVisibility(GONE);
        viewPager.setVisibility(VISIBLE);
    }

    private void hiedOtherViews() {
        mScaleImageView.setVisibility(VISIBLE);

        viewPager.setVisibility(GONE);
    }





    /**
     * 计算放大比例，开启放大动画
     *
     * @param photoData
     */
    private void calculateScaleAndStartZoomInAnim(final PhotoParameter photoData) {
        mScaleImageView.setVisibility(View.VISIBLE);

        // 放大动画参数
        int translationX = (photoData.locOnScreen[0] + photoData.imageWidth / 2) - (int) (mScreenRect.width() / 2);
        int translationY = (photoData.locOnScreen[1] + photoData.imageHeight / 2) - (int) ((mScreenRect.height() + GalleryScreenUtil.getStatusBarHeight(getContext())) / 2);
        float scale = getImageViewScale(photoData);
        // 开启放大动画
        executeZoom(mScaleImageView, translationX, translationY, scale, true, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                showOtherViews();
//                tvPhotoSize.setText(String.format("%d/%d", viewPager.getCurrentItem() + 1, photoList.size()));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 计算缩小比例，开启缩小动画
     */
    private void calculateScaleAndStartZoomOutAnim() {
        hiedOtherViews();

        // 缩小动画参数
        int translationX = (firstClickItemParameterModel.locOnScreen[0] + firstClickItemParameterModel.imageWidth / 2) - (int) (mScreenRect.width() / 2);
        int translationY = (firstClickItemParameterModel.locOnScreen[1] + firstClickItemParameterModel.imageHeight / 2) - (int) ((mScreenRect.height() + GalleryScreenUtil.getStatusBarHeight(getContext())) / 2);
        float scale = getImageViewScale(firstClickItemParameterModel);
        // 开启缩小动画
        executeZoom(mScaleImageView, translationX, translationY, scale, false, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                mScaleImageView.setImageDrawable(null);
                mScaleImageView.setVisibility(GONE);
                setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    /**
     * 执行缩放动画
     * @param scaleImageView
     * @param translationX
     * @param translationY
     * @param scale
     * @param isEnlarge
     */
    private void executeZoom(final ImageView scaleImageView, int translationX, int translationY, float scale, boolean isEnlarge, Animator.AnimatorListener listener) {
        float startTranslationX, startTranslationY, endTranslationX, endTranslationY;
        float startScale, endScale, startAlpha, endAlpha;

        // 放大
        if (isEnlarge) {
            startTranslationX = translationX;
            endTranslationX = 0;
            startTranslationY = translationY;
            endTranslationY = 0;
            startScale = scale;
            endScale = 1;
            startAlpha = 0f;
            endAlpha = 0.75f;
        }
        // 缩小
        else {
            startTranslationX = 0;
            endTranslationX = translationX;
            startTranslationY = 0;
            endTranslationY = translationY;
            startScale = 1;
            endScale = scale;
            startAlpha = 0.75f;
            endAlpha = 0f;
        }

        //-------缩小动画--------
        AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(scaleImageView, "translationX", startTranslationX, endTranslationX))
                .with(ObjectAnimator.ofFloat(scaleImageView, "translationY", startTranslationY, endTranslationY))
                .with(ObjectAnimator.ofFloat(scaleImageView, "scaleX", startScale, endScale))
                .with(ObjectAnimator.ofFloat(scaleImageView, "scaleY", startScale, endScale))
                // ---Alpha动画---
                // mMaskView伴随着一个Alpha减小动画
                .with(ObjectAnimator.ofFloat(maskView, "alpha", startAlpha, endAlpha));
        set.setDuration(animDuration);
        if (listener != null) {
            set.addListener(listener);
        }
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
    }

    private float getImageViewScale(PhotoParameter photoData) {
        float scale;
        float scaleX = photoData.imageWidth / mScreenRect.width();
        float scaleY = photoData.photoHeight * 1.0f / mScaleImageView.getHeight();

        // 横向图片
        if (photoData.photoWidth > photoData.photoHeight) {
            // 图片的宽高比
            float photoScale = photoData.photoWidth * 1.0f / photoData.photoHeight;
            // 执行动画的ImageView宽高比
            float animationImageScale = mScaleImageView.getWidth() * 1.0f / mScaleImageView.getHeight();

            if (animationImageScale > photoScale) {
                // 动画ImageView宽高比大于图片宽高比的时候，需要用图片的高度除以动画ImageView高度的比例尺
                scale = scaleY;
            }
            else {
                scale = scaleX;
            }
        }
        // 正方形图片
        else if (photoData.photoWidth == photoData.photoHeight) {
            if (mScaleImageView.getWidth() > mScaleImageView.getHeight()) {
                scale = scaleY;
            }
            else {
                scale = scaleX;
            }
        }
        // 纵向图片
        else {
            scale = scaleY;
        }
        return scale;
    }


}

package trunk.doi.base.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import java.util.ArrayList;

import trunk.doi.base.R;

/**
 *
 *
 */
public class ImageCycleView extends LinearLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;
    /**
     * 滚动图片视图适配
     */
    private ImageCycleAdapter mAdvAdapter;
    /**
     * 图片轮播指示器控件
     */
    private ViewGroup mGroup;

    /**
     * 图片轮播指示个图
     */
    private ImageView mImageView = null;

    /**
     * 滚动图片指示视图列表
     */
    private ImageView[] mImageViews = null;

    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 0;
    /**
     * 手机密度
     */
    private float mScale;
    private boolean isStop;

    /**
     * @param context
     */
    public ImageCycleView(Context context) {
        super(context);
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public ImageCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;
        LayoutInflater.from(context).inflate(R.layout.view_roll_pager, this);
        mAdvPager = (ViewPager) findViewById(R.id.vp_roll);
        mAdvPager.addOnPageChangeListener(new GuidePageChangeListener());
        mAdvPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        startImageTimerTask();
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        // 滚动图片右下指示器视
        mGroup = (ViewGroup) findViewById(R.id.ll_circle);
    }


    /**
     * 装填图片数据
     *
     * @param :
     * @param imageCycleViewListener
     */
    public <T> void  setImageResources(ArrayList<T> imageUrlList, ImageCycleViewListener imageCycleViewListener) {
        if (imageUrlList != null && imageUrlList.size() > 0) {
            this.setVisibility(View.VISIBLE);
        } else {
            this.setVisibility(View.GONE);
            return;
        }

        // 清除
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = imageUrlList.size();
        mImageViews = new ImageView[imageCount];
        for (int i = 0; i < imageCount; i++) {
            mImageView = new ImageView(mContext);
            int imageParams = (int) (mScale * 8 + 0.5f);// XP与DP转换，适应应不同分辨率
            int imagePadding = (int) (mScale * 5 + 0.5f);
            LayoutParams params = new LayoutParams(imageParams, imageParams);
            params.leftMargin = 10;
            mImageView.setScaleType(ScaleType.CENTER_CROP);
            mImageView.setLayoutParams(params);
            mImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);

            mImageViews[i] = mImageView;
            if (i == 0) {
                mImageViews[i].setBackgroundResource(R.drawable.shape_vp_selected);
            } else {
                mImageViews[i].setBackgroundResource(R.drawable.shape_vp_unselected);
            }
            mGroup.addView(mImageViews[i]);
        }

        mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList, imageCycleViewListener);
        mAdvPager.setAdapter(mAdvAdapter);
        startImageTimerTask();
    }

    /**
     * 图片轮播(手动控制自动轮播与否，便于资源控件）
     */
    public void startImageCycle() {
        startImageTimerTask();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void pauseImageCycle() {
        stopImageTimerTask();
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        // 图片滚动
        mHandler.postDelayed(mImageTimerTask, 3000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable() {
        @Override
        public void run() {
            if (mImageViews != null) {
                mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
                if (!isStop) {  //if  isStop=true   //当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环
                    mHandler.postDelayed(mImageTimerTask, 3000);
                }

            }
        }
    };

    /**
     * 轮播图片监听
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                startImageTimerTask();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int index) {
            index = index % mImageViews.length;
            // 设置当前显示的图片
            mImageIndex = index;
            // 设置图片滚动指示器背
            mImageViews[index].setBackgroundResource(R.drawable.shape_vp_selected);
            for (int i = 0; i < mImageViews.length; i++) {
                if (index != i) {
                    mImageViews[i].setBackgroundResource(R.drawable.shape_vp_unselected);
                }
            }
        }
    }

    private class ImageCycleAdapter<T> extends PagerAdapter {

        private ArrayList<ImageView> mImageViewCacheList;

        private ArrayList<T> mAdList = new ArrayList<>();

        private ImageCycleViewListener<T> mImageCycleViewListener;

        private Context mContext;

        public ImageCycleAdapter(Context context, ArrayList<T> adList, ImageCycleViewListener imageCycleViewListener) {
            this.mContext = context;
            this.mAdList = adList;
            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<>();
        }

        @Override
        public int getCount() {
//			return mAdList.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView;
            if (mImageViewCacheList.isEmpty()) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ScaleType.FIT_XY);
            } else {
                imageView = mImageViewCacheList.remove(0);
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageCycleViewListener.onImageClick(position % mAdList.size(), v);
                }
            });
            container.addView(imageView);
            mImageCycleViewListener.displayImage(mAdList.get(position % mAdList.size()), imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView view = (ImageView) object;
            mAdvPager.removeView(view);
            mImageViewCacheList.add(view);
        }
    }

    /**
     * 轮播控件的监听事件
     *
     * @author minking
     */
    public interface ImageCycleViewListener<T> {
        /**
         * 加载图片资源
         *
         * @param data
         * @param imageView
         */
        void displayImage(T data, ImageView imageView);

        /**
         * 单击图片事件
         *
         * @param position
         * @param imageView
         */
        void onImageClick(int position, View imageView);
    }



}
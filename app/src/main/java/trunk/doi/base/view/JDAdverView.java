package trunk.doi.base.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import trunk.doi.base.R;


/**
 * Created by zengyu on 2016/3/20.
 */
public class JDAdverView extends LinearLayout {
	// 控件高度
	private float mAdverHeight = 0f;
	// 间隔时间
	private final int mGap = 4000;
	// 动画间隔时间
	private final int mAnimDuration = 1000;
	private JDViewAdapter mAdapter;
	private final float jdAdverHeight = 50;
	// 显示的view
	private View mFirstView;
	private View mSecondView;
	// 播放的下标
	private int mPosition;
	// 线程的标识
	private boolean isStarted;

	public JDAdverView(Context context) {
		this(context, null);
	}

	public JDAdverView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public JDAdverView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	/**
	 * 初始化属性
	 *
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	private void init(Context context, AttributeSet attrs, int defStyleAttr) {
		// 设置为垂直方向
		setOrientation(VERTICAL);
		// 获取自定义属性
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.JDAdverView);
		mAdverHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				jdAdverHeight, getResources().getDisplayMetrics());
		int gap = array.getInteger(R.styleable.JDAdverView_gap, mGap);
		int animDuration = array.getInteger(
				R.styleable.JDAdverView_animDuration, mAnimDuration);

		if (mGap <= mAnimDuration) {
			gap = mGap;
			animDuration = mAnimDuration;
		}
		// 关闭清空TypedArray
		array.recycle();
	}

	/**
	 * 设置数据
	 */
	public void setAdapter(JDViewAdapter adapter) {
		this.mAdapter = adapter;
		setupAdapter();
	}

	/**
	 * 开启线程
	 */
	public void start() {

		if (!isStarted && mAdapter.getCount() > 1) {
			isStarted = true;
			postDelayed(mRunnable, mGap);// 间隔mgap刷新一次UI
		}
	}

	/**
	 * 暂停滚动
	 */
	public void stop() {
		// 移除handle更新
		removeCallbacks(mRunnable);
		// 暂停线程
		isStarted = false;
	}

	/**
	 * 设置数据适配
	 */
	private void setupAdapter() {
		// 移除所有view
		removeAllViews();
		// 只有一条数据,不滚东
		if (mAdapter.getCount() == 1) {
			mFirstView = mAdapter.getView(this);
			mAdapter.setItem(mFirstView, mAdapter.getItem(0));
			addView(mFirstView);
		} else {
			// 多个数据
			mFirstView = mAdapter.getView(this);
			mSecondView = mAdapter.getView(this);
			mAdapter.setItem(mFirstView, mAdapter.getItem(0));
			mAdapter.setItem(mSecondView, mAdapter.getItem(1));
			// 把2个添加到此控件里
			addView(mFirstView);
			addView(mSecondView);
			mPosition = 1;
			isStarted = false;
		}
	}

	/**
	 * 测量控件的宽高
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (LayoutParams.WRAP_CONTENT == getLayoutParams().height) {
			getLayoutParams().height = (int) mAdverHeight;
		} else {
			mAdverHeight = getHeight();
		}
		if (mFirstView != null) {
			mFirstView.getLayoutParams().height = (int) mAdverHeight;
		}
		if (mSecondView != null) {
			mSecondView.getLayoutParams().height = (int) mAdverHeight;
		}
	}



	/**
	 * 垂直滚蛋
	 */
	private void performSwitch() {
		// 属性动画控制控件滚动，y轴方向移动
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(mFirstView,
				"translationY", mFirstView.getTranslationY() - mAdverHeight);
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSecondView,
				"translationY", mSecondView.getTranslationY() - mAdverHeight);
		// 动画集
		AnimatorSet set = new AnimatorSet();
		set.playTogether(animator1, animator2);// 2个动画一起
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {// 动画结束
				mFirstView.setTranslationY(0);
				mSecondView.setTranslationY(0);
				View removedView = getChildAt(0);// 获得第一个子布局
				mPosition++;
				// 设置显示的布局
				mAdapter.setItem(removedView,
						mAdapter.getItem(mPosition % mAdapter.getCount()));
				// 移除前一个view
				removeView(removedView);
				// 添加下一个view
				addView(removedView, 1);
			}
		});
		set.setDuration(mAnimDuration);// 持续时间
		set.start();// 开启动画
	}

	private AnimRunnable mRunnable = new AnimRunnable();

	private class AnimRunnable implements Runnable {
		@Override
		public void run() {
			performSwitch();
			postDelayed(this, mGap);
		}
	}

	/**
	 * 销毁View的时候调用
	 */
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		// 停止滚动
		stop();
	}

	/**
	 * 屏幕 旋转
	 *
	 * @param newConfig
	 */
	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}

package trunk.doi.base.base.adapter.commonadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by ly on 2016/5/30 14:53.
 * 通用的ViewHolder
 */
public class CommonViewHolder {
    private SparseArray<View> mViews;
    private static boolean conver = true;
    private View mConverView;

    /**
     * @param context
     * @param parent
     * @param layoutId Item的布局文件
     */
    private CommonViewHolder(Context context, ViewGroup parent, int layoutId,
                             int position) {
        this.mViews = new SparseArray<View>();
        this.mConverView = LayoutInflater.from(context).inflate(layoutId,
                parent, false);
        this.mConverView.setTag(this);
    }

    /**
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @return
     */
    public static CommonViewHolder get(Context context, View convertView,
                                       ViewGroup parent, int layoutId, int position) {
        CommonViewHolder viewHolder = null;
        if (conver) {
            if (convertView == null) {
                viewHolder = new CommonViewHolder(context, parent, layoutId,
                        position);
            } else {
                viewHolder = (CommonViewHolder) convertView.getTag();
            }
        } else {
            viewHolder = new CommonViewHolder(context, parent, layoutId,
                    position);
        }
        return viewHolder;
    }

    /**
     * 通过id获取组件 注意：不要将组件声明到全局，否则获取到的永远是最后一个组件
     *
     * @param viewId 控件ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return this.mConverView;
    }


    /**
     * TextView
     *
     * @param viewId
     * @param text   string
     * @return
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param number float
     * @return
     */
    public CommonViewHolder setText(int viewId, float number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param number int
     * @return
     */
    public CommonViewHolder setText(int viewId, int number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * TextView
     *
     * @param viewId
     * @param number double
     * @return
     */
    public CommonViewHolder setText(int viewId, Double number) {
        StringBuilder builder = new StringBuilder();
        TextView tv = getView(viewId);
        tv.setText(builder.append(number));
        return this;
    }

    /**
     * Button
     *
     * @param viewId
     * @param text   string
     * @return
     */
    public CommonViewHolder setButton(int viewId, String text) {
        Button btn = getView(viewId);
        btn.setText(text);
        return this;
    }

    /**
     * 通过资源ID设置ImageView
     *
     * @param viewId
     * @param resId  资源ID
     * @return
     */
    public CommonViewHolder setImageById(int viewId, int resId) {
        ImageView image = getView(viewId);
        image.setImageResource(resId);
        return this;
    }

    /**
     * 通过bimap设置ImageView
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public CommonViewHolder setImageByBitmap(int viewId, Bitmap bitmap) {
        ImageView image = getView(viewId);
        image.setImageBitmap(bitmap);
        return this;
    }


    /**
     * Button点击事件
     *
     * @param viewId
     * @param listener
     */
    public CommonViewHolder setButtonListener(int viewId,
                                              View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * Button长按监听
     *
     * @param viewId
     * @param listener
     * @return
     */
    public CommonViewHolder setButtonLongListener(int viewId,
                                                  View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    /**
     * TextView点击事件
     *
     * @param viewId
     * @param listener
     */
    public CommonViewHolder setTextListener(int viewId,
                                            View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * ImageView点击事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public CommonViewHolder setImageListener(int viewId,
                                             View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public CommonViewHolder setRatingBar(int viewId, int rating) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(rating);
        return this;
    }
}

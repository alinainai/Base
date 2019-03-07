package trunk.doi.base.util.glideutils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;

import trunk.doi.base.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 *
 * 图片加载帮助类
 */
public final class ImageLoader {


    private ImageLoader() {
        throw new RuntimeException("ImageLoader cannot be initialized!");
    }


    public static void loadSet(Context context, String url, ImageView view,int defaultResId) {


        RequestOptions options = new RequestOptions()
                .error(defaultResId)
                .placeholder(defaultResId)
                .centerCrop();
        GlideApp.with(context).load(url)
                .transition(withCrossFade())
                .apply(options)
                .into(view);

    }
    public static void loadDefault(Context context, String url, ImageView view) {

        loadSet(context,url,view,R.mipmap.img_data_empty);


    }




}

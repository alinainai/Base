package com.base.lib.https.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageLoader {

    private ImageLoader() {
        throw new RuntimeException("ImageLoader cannot be initialized!");
    }


    public static void loadSet(Context context, String url, ImageView view, int defaultResId) {


        RequestOptions options = new RequestOptions()
                .error(defaultResId)
                .placeholder(defaultResId)
                .centerCrop();
        GlideArms.with(context).load(url)
                .transition(withCrossFade())
                .apply(options)
                .into(view);

    }



}

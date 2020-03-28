package com.base.baseui.photoshow.view;

import android.content.Context;

import com.base.baseui.photoshow.model.ILoadImage;
import com.base.baseui.photoshow.model.IPhotoProvider;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class WrapPhotoView extends PhotoView implements ILoadImage {

    private IPhotoProvider photoModel;

    public WrapPhotoView(Context context, IPhotoProvider photoModel) {
        super(context);
        this.photoModel = photoModel;
    }


    @Override
    public void loadImage() {
        Glide.with(getContext()).load(photoModel.providePhoto()).into(WrapPhotoView.this);
    }
}

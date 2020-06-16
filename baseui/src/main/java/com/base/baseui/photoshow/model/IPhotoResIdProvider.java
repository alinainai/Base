package com.base.baseui.photoshow.model;


import androidx.annotation.IdRes;

public interface IPhotoResIdProvider extends IPhotoProvider<Integer> {
    @IdRes
    Integer providePhoto();
}

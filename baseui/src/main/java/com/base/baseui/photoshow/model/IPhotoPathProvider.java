package com.base.baseui.photoshow.model;



public interface IPhotoPathProvider extends IPhotoProvider<String> {
    String providePhoto();


    public class PhotoPathProvider implements IPhotoPathProvider{

        private String imgPath = "";

        public PhotoPathProvider(String str){
            this.imgPath = str;
        }

        @Override
        public String providePhoto() {
            return imgPath;
        }
    }
}

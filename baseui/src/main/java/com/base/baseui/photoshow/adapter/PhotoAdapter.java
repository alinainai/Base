package com.base.baseui.photoshow.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.base.baseui.photoshow.model.ILoadImage;
import com.base.baseui.photoshow.view.WrapPhotoView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * desc:
 *
 * created by author ljx 
 * date  2020/3/28
 * email 569932357@qq.com
 *
 * ================================================
 */

public class PhotoAdapter extends PagerAdapter {

    private List<ILoadImage> viewList = new ArrayList<>();
    private List<Integer> positions = new ArrayList<>();
    public PhotoAdapter(List<WrapPhotoView> list) {
        if(list != null) {
            viewList.addAll(list);
        }
    }

    @Override
    public int getCount() {
        return this.viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        if (!positions.contains(position)) {
            positions.add(position);
            //创建一个新的item
            viewList.get(position).loadImage();
        }
        container.addView((View) viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)viewList.get(position));
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
        return POSITION_NONE;
    }

}

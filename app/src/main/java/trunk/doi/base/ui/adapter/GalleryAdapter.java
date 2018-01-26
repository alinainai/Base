package trunk.doi.base.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import trunk.doi.base.R;
import trunk.doi.base.bean.GankItemData;

/**
 * 作者：Mr.Lee on 2017-7-3 14:24
 * 邮箱：569932357@qq.com
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    /**
     * ItemClick的回调接口
     *
     * @author zhy
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private LayoutInflater mInflater;
    private List<GankItemData> mDatas;
    private Context mContext;

    public GalleryAdapter(Context context, List<GankItemData> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView mImg;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_hr_rl,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.img_banner);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Glide.with(mContext).load(mDatas.get(i).getUrl()).into(viewHolder.mImg);

        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
                }
            });
        }

    }


}

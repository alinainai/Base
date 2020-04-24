package com.base.baseui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.RecyclerView;

import com.base.baseui.R;
import com.base.lib.util.Preconditions;
import com.base.paginate.base.MultiAdapter;
import com.base.paginate.base.SingleAdapter;
import com.base.paginate.viewholder.PageViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


public class SelectBottomDialog {


    public static final int MODE_CLICK = 1;
    public static final int MODE_SELECT = 2;
    public static final int MODE_CHECK = 3;

    // 自定义一个注解MyState
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_CLICK, MODE_SELECT, MODE_CHECK})
    public @interface Mode {
    }

    private Context mContext;
    private CommonBottomDialog.Builder mBuilder;
    private onItemOptionListener mListener;
    private @Mode
    int mMode = MODE_CLICK;

    private SelectBottomDialog(Context context) {
        mContext = context;
        mBuilder = new CommonBottomDialog.Builder(context);
    }


    public static SelectBottomDialog getInstance(Context context) {
        return new SelectBottomDialog(context);
    }

    public SelectBottomDialog setCancelable(boolean isCancelable) {
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first");
        mBuilder.setCancelable(isCancelable);
        return this;
    }

    public SelectBottomDialog setOnItemOptionListener(onItemOptionListener listener) {
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first");
        mBuilder.setDialogClickListener(listener);
        mListener = listener;
        return this;
    }

    public SelectBottomDialog setMode(@Mode int mode) {
        Preconditions.checkNotNull(mContext, "You should call getInstance first");
        mMode = mode;
        return this;
    }

    public void show(){
        Preconditions.checkNotNull(mBuilder, "You should call getInstance first");
        View view = LayoutInflater.from(mContext).inflate(R.layout.public_dialog_bottom_select, null);
        mBuilder.setCustomView(view);

    }

    public interface onItemOptionListener extends CommonBottomDialog.onDialogClickListener {


        default void onItemClickListener(int itemId) {

        }

        default void onItemSelectListener(int itemId) {

        }

        default void onItemCheckSureListener(List<Integer> itemIds) {

        }

    }


    private class ItemAdapter extends MultiAdapter<String> {

        private int mMode;

        public ItemAdapter(Context context,@Mode int mode) {
            super(context, false, false);
            this.mMode=mode;
        }


        @Override
        protected int getItemLayoutId(int viewType) {
            return 0;
        }

        @Override
        protected int getViewType(int position, String data) {
            return 0;
//            switch (mMode){
//                case MODE_CLICK:
//                    break;
//                    case MODE_CLICK:
//                    break; case MODE_CLICK:
//                    break;
//            }
//            return ;
        }



        @Override
        protected void convert(PageViewHolder holder, String data, int position, int viewType) {

        }
    }

    public static class SelectBottomItem{

        private int mId;
        private String mName;

        private SelectBottomItem(int id,String name){
            mId=id;
           mName =name;
        }

        public static SelectBottomItem create(int id,String name){
            return new SelectBottomItem(id,name);
        }

        public int getId(){
            return mId;
        }

        public String getName(){
            return mName;
        }
    }


}



package com.gas.test.ui.fragment.adapter;

import android.content.Context;

import com.base.paginate.base.MultiAdapter;
import com.base.paginate.viewholder.PageViewHolder;
import com.gas.test.R;

public class SimpleMultiAdapter extends MultiAdapter<String> {


    private static final int STICKHEADER = 10001;
    private static final int COMMONITEM = 10002;

    public SimpleMultiAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.test_item_simple_item;
    }

    @Override
    protected int getViewType(int position, String data) {
        if(position!=0 && position%10==0)
            return STICKHEADER;
        return  COMMONITEM;
    }

    @Override
    protected void convert(PageViewHolder holder, String data, int position, int viewType) {
        holder.setText(R.id.item_desc, data);
    }



    public int getHeaderStickType(){
        return STICKHEADER;
    }

//    @Override
//    public int findNextNeedDockItemPos(int pos) {
//        if(pos>10){
//            if(pos%10==0){
//                return (pos/10+1)*10;
//            }else{
//                return (pos/10+1)*10;
//            }
//
//        }else {
//            return -1;
//        }
//    }
//
//    @Override
//    public int findPreviousNeedDockItemPos(int pos) {
//
//        if(pos>10){
//            if(pos%10==0){
//                return (pos/10-1)*10;
//            }else{
//                return pos/10*10;
//            }
//
//        }else {
//            return -1;
//        }
//
//    }
//
//    @Override
//    public boolean isItemNeedDockTop(int pos) {
//        return pos!=0 && pos%10==0;
//    }
}

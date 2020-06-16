package com.base.baseui.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.base.paginate.base.MultiAdapter;
import com.base.paginate.interfaces.EmptyInterface;
import com.base.paginate.interfaces.FooterInterface;
/**
 * ================================================
 * desc: 可定制 footerView 和 emptyView 的适配器
 *
 * created by author ljx
 * Date  2020/3/18
 * email 569932357@qq.com
 *
 * ================================================
 */
public abstract class ExtendMultiAdapter<T> extends MultiAdapter<T> {



    public ExtendMultiAdapter(Context context) {
        super(context);
    }

    public ExtendMultiAdapter(Context context, boolean openLoadMore) {
        super( context, openLoadMore);
    }

    public ExtendMultiAdapter(Context context, boolean openLoadMore, boolean openEmpty) {
        super(context, openLoadMore, openEmpty);
    }

    @Override
    protected EmptyInterface getEmptyLayout(Context context) {
        ExtendEmptyView emptyView = new ExtendEmptyView(context);
        if (!TextUtils.isEmpty(getDataEmptyStr())) {
            emptyView.setDataEmptyStr(getDataEmptyStr());
        }
        if (getDataEmptyImgRes() != -1) {
            emptyView.setDataEmptyRes(getDataEmptyImgRes());
        }

        return emptyView;
    }

    @Override
    protected FooterInterface getFooterLayout(Context context) {
        return new ExtendLoadFooter(context);
    }

    protected String getDataEmptyStr() {
        return "";
    }

    protected int getDataEmptyImgRes() {
        return -1;
    }



}

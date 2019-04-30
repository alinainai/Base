package com.base.baseui.view.status;

import android.view.View;


public class DefaultAdapter implements Gloading.Adapter {

    @Override
    public View getView(Gloading.Holder holder, View convertView, int status) {
        DefaultStatusView loadingStatusView = null;
        //reuse the old view, if possible
        if (convertView instanceof DefaultStatusView) {
            loadingStatusView = (DefaultStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new DefaultStatusView(holder.getContext(), holder.getRetryTask());
        }
        loadingStatusView.setStatus(status);

        return loadingStatusView;
    }

}

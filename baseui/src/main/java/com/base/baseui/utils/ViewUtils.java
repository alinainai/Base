package com.base.baseui.utils;

import android.widget.EditText;

public class ViewUtils {

    public static String getNoSpaceText(EditText et) {
        if(et==null)
            return "";
        return (et.getText() != null ? et.getText().toString().trim().replaceAll(" ", "") : "");
    }


}

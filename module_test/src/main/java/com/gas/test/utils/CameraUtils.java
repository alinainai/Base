package com.gas.test.utils;

import com.lib.commonsdk.kotlin.extension.app.LogExtKt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import static com.lib.commonsdk.kotlin.extension.app.LogExtKt.errorLog;

public class CameraUtils {
    public static String CHINA = "CN";

    public static String getLocaleCountry() {
        Locale locale = Locale.getDefault();
        try {
            return URLEncoder.encode(locale.getCountry(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return locale.getCountry();
    }

    public static String getLocaleLanguage() {
        Locale locale = Locale.getDefault();
        try {
            return URLEncoder.encode(locale.getLanguage(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return locale.getLanguage();
    }

    public static String localStringToUrl() {
        try {
            return URLEncoder.encode(Locale.getDefault().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return Locale.getDefault().toString();
    }

}

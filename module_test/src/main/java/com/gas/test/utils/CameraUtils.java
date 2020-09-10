package com.gas.test.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.lib.commonsdk.kotlin.extension.app.LogExtKt;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static String getDeviceCountryCode(Context context) {
        String countryCode;

        // try to get country code from TelephonyManager service
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(tm != null) {
            // query first getSimCountryIso()
            countryCode = tm.getSimCountryIso();
            if (countryCode != null && countryCode.length() == 2)
                return countryCode.toLowerCase();

            if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                // special case for CDMA Devices
                countryCode = getCDMACountryIso();
            } else {
                // for 3G devices (with SIM) query getNetworkCountryIso()
                countryCode = tm.getNetworkCountryIso();
            }

            if (countryCode != null && countryCode.length() == 2)
                return countryCode.toLowerCase();
        }

        // if network country not available (tablets maybe), get country code from Locale class
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            countryCode = context.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            countryCode = context.getResources().getConfiguration().locale.getCountry();
        }

        if (countryCode != null && countryCode.length() == 2)
            return  countryCode.toLowerCase();

        // general fallback to"us"
        return"us";
    }

    @SuppressLint("PrivateApi")
    private static String getCDMACountryIso() {
        try {
            // try to get country code from SystemProperties private class
            Class< ? > systemProperties = Class.forName("android.os.SystemProperties");
            Method get = systemProperties.getMethod("get", String.class);

            // get homeOperator that contain MCC + MNC
            String homeOperator = ((String) get.invoke(systemProperties,
                    "ro.cdma.home.operator.numeric"));

            // first 3 chars (MCC) from homeOperator represents the country code
            int mcc = Integer.parseInt(homeOperator.substring(0, 3));

            // mapping just countries that actually use CDMA networks
            switch (mcc) {
                case 330: return"PR";
                case 310: return"US";
                case 311: return"US";
                case 312: return"US";
                case 316: return"US";
                case 283: return"AM";
                case 460: return"CN";
                case 455: return"MO";
                case 414: return"MM";
                case 619: return"SL";
                case 450: return"KR";
                case 634: return"SD";
                case 434: return"UZ";
                case 232: return"AT";
                case 204: return"NL";
                case 262: return"DE";
                case 247: return"LV";
                case 255: return"UA";
            }
        } catch (ClassNotFoundException ignored) {
        } catch (NoSuchMethodException ignored) {
        } catch (IllegalAccessException ignored) {
        } catch (InvocationTargetException ignored) {
        } catch (NullPointerException ignored) {
        }

        return null;
    }

}

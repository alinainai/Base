package com.gas.test.utils.view.tablayout.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class TabUtils {

    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public static int computeColor(@ColorInt int fromColor, @ColorInt int toColor, float fraction) {
        fraction = constrain(fraction, 0f, 1f);

        int minColorA = Color.alpha(fromColor);
        int maxColorA = Color.alpha(toColor);
        int resultA = (int) ((maxColorA - minColorA) * fraction) + minColorA;

        int minColorR = Color.red(fromColor);
        int maxColorR = Color.red(toColor);
        int resultR = (int) ((maxColorR - minColorR) * fraction) + minColorR;

        int minColorG = Color.green(fromColor);
        int maxColorG = Color.green(toColor);
        int resultG = (int) ((maxColorG - minColorG) * fraction) + minColorG;

        int minColorB = Color.blue(fromColor);
        int maxColorB = Color.blue(toColor);
        int resultB = (int) ((maxColorB - minColorB) * fraction) + minColorB;

        return Color.argb(resultA, resultR, resultG, resultB);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 把以 dp 为单位的值，转化为以 px 为单位的值
     *
     * @param dpValue 以 dp 为单位的值
     * @return px value
     */
    public static int dpToPx(Context context,int dpValue) {
        float density= context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int getNumberDigits(int number) {
        if (number <= 0) return 0;
        return (int) (Math.log10(number) + 1);
    }

    public static String formatNumberToLimitedDigits(int number, int maxDigits) {
        if (getNumberDigits(number) > maxDigits) {
            StringBuilder result = new StringBuilder();
            for (int digit = 1; digit <= maxDigits; digit++) {
                result.append("9");
            }
            result.append("+");
            return result.toString();
        } else {
            return String.valueOf(number);
        }
    }

}

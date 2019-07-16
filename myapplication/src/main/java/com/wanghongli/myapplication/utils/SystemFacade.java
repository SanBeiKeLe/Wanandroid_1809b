package com.wanghongli.myapplication.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/*
 * created by taofu on 2019-06-18
 **/
public class SystemFacade {


    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static float getDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    public static int getDensityDpi(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.densityDpi;
    }

    /**
     * dp  转 px
     * @param context
     * @param dp  传入多少dp
     * @return  返回dp 换算成px 后的值
     */
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);

    }

}

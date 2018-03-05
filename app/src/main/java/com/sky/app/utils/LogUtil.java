package com.sky.app.utils;

import android.content.Context;
import android.util.Log;


public class LogUtil {
    private static boolean isDebug = true;

    public static void e(String String) {
        if (isDebug)
            Log.e("==========", "==========" + String);
    }

    public static void e(String str0, String str1) {
        if (isDebug)
            Log.e("==========", "==========" + str0 + "=" + str1);
    }

    public static void e(Context context, String String) {
        if (isDebug)
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + String);
    }

    public static void e(Class clazz, String String) {
        if (isDebug)
            Log.e("==========" + clazz.getSimpleName(), "==========" + String);
    }

    public static void e(Context context, int Int) {
        if (isDebug)
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + Int);
    }

    public static void e(Context context, float Float) {
        if (isDebug)
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + Float);
    }

    public static void e(Context context, double Double) {
        if (isDebug)
            Log.e("==========" + context.getClass().getSimpleName(), "==========" + Double);
    }
}

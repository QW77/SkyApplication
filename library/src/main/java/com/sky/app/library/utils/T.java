package com.sky.app.library.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public final class T {

    private T() {
        throw new UnsupportedOperationException("T cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     * @param context
     * @param message
     */
    public static void showShort(final Context context, final CharSequence message) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 长时间显示Toast
     * @param context
     * @param message
     */
    public static void showLong(final Context context, final CharSequence message) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
    
}
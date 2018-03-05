package com.sky.app.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static Toast toast = null;

    public static void longshow(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void longshow(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    public static void shortshow(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void shortshow(Context context, int resId) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }
}

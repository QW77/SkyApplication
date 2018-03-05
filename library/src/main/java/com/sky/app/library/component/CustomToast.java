/*
 * FILE NAME:  CustomToast.java
 * COPYRIGHT:  Copyright (c) 2006-2015 ICS&S Inc,  All rights reserved
 * DESCRIPTION:  <Description>
 * USER:  hl
 * DATE:  2015-8-8
 * MODIFY VERSION:  <Modify version>
 * CHANGE RESUMES:  <Change resumes>
 */
package com.sky.app.library.component;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.app.library.R;
import com.sky.app.library.utils.AppUtils;

/**
 * 项目名称：library
 * 类描述： 弹幕提示类
 * 创建人： huanglei
 * 修改备注：
 */
public class CustomToast {

    private static Toast getToast(Context context, String text) {
        TextView toastRoot = new TextView(context);
        toastRoot.setMinWidth(AppUtils.dip2px(context, 100));
        toastRoot.setText(text);
        toastRoot.setGravity(Gravity.CENTER);
        toastRoot.setBackgroundResource(R.drawable.corners_bg);
        toastRoot.setTextSize(13);
        toastRoot.setTextColor(Color.WHITE);
        int pxX = AppUtils.dip2px(context, 10);
        int pxY = AppUtils.dip2px(context, 30);
        toastRoot.setPadding(pxX, pxY, pxX, pxY);

        Toast toastStart = new Toast(context);
        toastStart.setGravity(Gravity.CENTER, 0, 0);
        toastStart.setView(toastRoot);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        return toastStart;
    }

    public static void showToast(Context mContext, String text){
        if(Looper.getMainLooper() == Looper.myLooper()){
            CustomToast.getToast(mContext, text).show();
        } else {
            Looper.prepare();
            CustomToast.getToast(mContext, text).show();
            Looper.loop();
        }
    }
}

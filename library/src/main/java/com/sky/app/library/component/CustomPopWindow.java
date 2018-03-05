package com.sky.app.library.component;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 自定义popwindow
 * Created by sky on 2017/5/10.
 */

public class CustomPopWindow extends PopupWindow{
    private Activity activity;

    public CustomPopWindow(Activity activity, View contentView, int width, int height) {
        super(contentView, width, height);
        this.activity = activity;
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                activity.getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
        super.showAsDropDown(anchor, xoff, yoff);
    }
}

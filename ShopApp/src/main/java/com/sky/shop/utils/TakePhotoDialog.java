package com.sky.shop.utils;

import android.app.Activity;
import android.app.Dialog;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.sky.app.library.utils.Md5Util;
import com.sky.app.library.utils.StringUtil;
import com.sky.shop.R;

/**
 * 拍照dialog
 * Created by sky on 2017/3/18.
 */

public class TakePhotoDialog extends Dialog {
    private Activity activity = null;
    private Button takePhoto, pickPhoto, cancel;

    public TakePhotoDialog(@NonNull Activity activity) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_photo_layout);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.popupAnimation);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        //初始化
        initView();
        initClick();
    }

    /**
     * 初始化事件
     */
    private void initClick() {
        this.takePhoto.setOnClickListener(onClick);//拍照
        this.pickPhoto.setOnClickListener(onClick);//选取照片
        this.cancel.setOnClickListener(onClick);//取消
    }

    /**
     * 初始化控件
     */
    private void initView() {
        this.takePhoto = (Button) findViewById(R.id.driver_take_photo_btn);
        this.pickPhoto = (Button) findViewById(R.id.driver_pick_photo_btn);
        this.cancel = (Button) findViewById(R.id.driver_cancel_btn);
    }

    /**
     * 点击事件
     */
    private View.OnClickListener onClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.driver_take_photo_btn) {
                TakePhotoUtils.getInstance(activity).takePoto(Md5Util.md5("测试" + StringUtil.randStr(6)) + ".png");
                dismiss();

            } else if (i == R.id.driver_pick_photo_btn) {
                TakePhotoUtils.getInstance(activity).choosePhoto(Md5Util.md5("测试11" + StringUtil.randStr(6)) + ".png");
                dismiss();

            } else if (i == R.id.driver_cancel_btn) {
                dismiss();
            }
        }
    };


}

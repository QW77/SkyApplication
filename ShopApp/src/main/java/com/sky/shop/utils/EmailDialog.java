package com.sky.shop.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.ui.activity.shop.SafeCenterActivity;

/**
 * 修改邮箱
 * Created by sky on 2017/3/18.
 */

public class EmailDialog extends Dialog{
    private Activity activity = null;

    private SafeCenterActivity.IBindEmailCallBack iBindEmailCallBack;
    Button confirm;
    EditText email;

    public EmailDialog(@NonNull Activity activity, SafeCenterActivity.IBindEmailCallBack iBindEmailCallBack) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
        this.iBindEmailCallBack = iBindEmailCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bind_email_layout);
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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString().trim())){
                    T.showShort(activity, "请输入邮箱");
                    return;
                }
                if (null != iBindEmailCallBack){
                    iBindEmailCallBack.bind(email.getText().toString().trim());
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        confirm = (Button) findViewById(R.id.app_confirm_btn);
        email = (EditText) findViewById(R.id.app_email);
    }
}

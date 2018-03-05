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
 * 修改登录密码
 * Created by sky on 2017/3/18.
 */

public class PwdDialog extends Dialog{
    private Activity activity = null;

    private SafeCenterActivity.IPwdCallBack iPwdCallBack;
    Button confirm;
    EditText oldPwd;
    EditText newPwd;

    public PwdDialog(@NonNull Activity activity, SafeCenterActivity.IPwdCallBack iPwdCallBack) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
        this.iPwdCallBack = iPwdCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_update_pwd_layout);
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
                if (TextUtils.isEmpty(oldPwd.getText().toString().trim())){
                    T.showShort(activity, "请输入原密码");
                    return;
                }
                if (TextUtils.isEmpty(newPwd.getText().toString().trim())){
                    T.showShort(activity, "请输入新密码");
                    return;
                }
                if (null != iPwdCallBack){
                    iPwdCallBack.pwd(oldPwd.getText().toString().trim(), newPwd.getText().toString().trim());
                }
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        confirm = (Button) findViewById(R.id.app_confirm_btn);
        oldPwd = (EditText) findViewById(R.id.app_old_pwd);
        newPwd = (EditText) findViewById(R.id.app_new_pwd);
    }
}

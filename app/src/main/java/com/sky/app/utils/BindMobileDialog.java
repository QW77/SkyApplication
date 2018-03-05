package com.sky.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sky.app.R;
import com.sky.app.bean.UserBean;
import com.sky.app.library.utils.Captcha;
import com.sky.app.library.utils.StringUtil;
import com.sky.app.library.utils.T;
import com.sky.app.ui.activity.user.SettingsActivity;

/**
 * 绑定手机号码
 * Created by sky on 2017/3/18.
 */

public class BindMobileDialog extends Dialog{
    private Activity activity = null;

    private SettingsActivity.IBindMobileCallBack iBindMobileCallBack;
    EditText mobile;
    EditText code;
    Button sendCode;
    Button bindMobile;
    boolean isCanClose;

    public BindMobileDialog(@NonNull Activity activity, SettingsActivity.IBindMobileCallBack iBindMobileCallBack, boolean isCanClose) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
        this.iBindMobileCallBack = iBindMobileCallBack;
        this.isCanClose = isCanClose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bind_mobile_layout);
        setCanceledOnTouchOutside(isCanClose);
        setCancelable(isCanClose);

        //初始化
        initView();
        initClick();
    }

    /**
     * 初始化事件
     */
    private void initClick() {
        sendCode.setOnClickListener(onclick);
        bindMobile.setOnClickListener(onclick);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mobile = (EditText) findViewById(R.id.app_mobile);
        code = (EditText) findViewById(R.id.app_code);
        sendCode = (Button) findViewById(R.id.app_send_code);
        bindMobile = (Button) findViewById(R.id.app_bind_mobile_btn);
    }

    /**
     * 事件处理
     */
    private View.OnClickListener onclick = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.app_send_code:
                    if (TextUtils.isEmpty(mobile.getText().toString().trim())){
                        T.showShort(activity, "手机号码不能为空！");
                        return;
                    }
                    if (!StringUtil.isPhone(mobile.getText().toString().trim())){
                        T.showShort(activity, "手机号格式不正确！");
                        return;
                    }
                    if (null != iBindMobileCallBack){
                        new Captcha(60000, 1000, sendCode).start();
                        iBindMobileCallBack.send(mobile.getText().toString().trim());
                    }
                    break;
                case R.id.app_bind_mobile_btn:
                    if (TextUtils.isEmpty(mobile.getText().toString().trim())){
                        T.showShort(activity, "手机号码不能为空！");
                        return;
                    }
                    if (!StringUtil.isPhone(mobile.getText().toString().trim())){
                        T.showShort(activity, "手机号格式不正确！");
                        return;
                    }
                    if (TextUtils.isEmpty(code.getText().toString().trim())){
                        T.showShort(activity, "验证码不为空！");
                        return;
                    }
                    if (!UserBean.getInstance().getCode().equals(code.getText().toString().trim())){
                        T.showShort(activity, "验证码不能为空！");
                        return;
                    }
                    if (null != iBindMobileCallBack){
                        iBindMobileCallBack.bind(mobile.getText().toString().trim());
                    }
                    break;
            }
        }
    };
}

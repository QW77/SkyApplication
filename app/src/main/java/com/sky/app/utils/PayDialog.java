package com.sky.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.sky.app.R;

/**
 * 支付界面
 * Created by sky on 2017/3/18.
 */

public class PayDialog extends Dialog{
    private Activity activity = null;
    private IPayCallback iPayCallback;

    private RelativeLayout alipayRl;
    private RelativeLayout weixinRl;
    private RadioButton alipay;
    private RadioButton weixin_pay;
    private Button cancel, confirm;

    private int flag = 0;//0：支付宝 1：微信

    public PayDialog(@NonNull Activity activity, IPayCallback iPayCallback) {
        super(activity, R.style.CustomDialog);
        this.activity = activity;
        this.iPayCallback = iPayCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_pay_layout);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        //初始化
        initView();
        initClick();
    }

    /**
     * 初始化事件
     */
    private void initClick() {
        alipayRl.setOnClickListener(click);
        weixinRl.setOnClickListener(click);
        cancel.setOnClickListener(click);
        confirm.setOnClickListener(click);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        alipayRl = (RelativeLayout) findViewById(R.id.alipay);
        weixinRl = (RelativeLayout) findViewById(R.id.weixin_pay);
        alipay = (RadioButton) findViewById(R.id.app_alipay);
        weixin_pay = (RadioButton) findViewById(R.id.app_weixin_pay);
        cancel = (Button) findViewById(R.id.app_cancel_btn);
        confirm = (Button) findViewById(R.id.app_confirm_btn);
        alipay.setChecked(true);
    }

    /**
     * 点击事件
     */
    private View.OnClickListener click = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.alipay:
                    alipay.setChecked(true);
                    weixin_pay.setChecked(false);
                    flag = 0;
                    break;
                case R.id.weixin_pay:
                    alipay.setChecked(false);
                    weixin_pay.setChecked(true);
                    flag = 1;
                    break;
                case R.id.app_cancel_btn:
                    dismiss();
                    break;
                case R.id.app_confirm_btn:
                    if (null != iPayCallback){
                        iPayCallback.pay(flag);
                    }
                    break;
            }
        }
    };

    /**
     * 支付回调接口
     */
    public interface IPayCallback{
        void pay(int flag);
    }
}

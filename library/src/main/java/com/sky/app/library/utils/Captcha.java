/*
 * 文 件 名:  Captcha.java
 * 版    权:  Copyright (c) 2006-2014 ICS&S Inc,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  hl
 * 修改时间:  2014-11-8
 * 修改版本号:  <版本编号>
 * 修改履历:  <修改内容>
 */
package com.sky.app.library.utils;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * 按钮上的倒计时
 * 
 * @author hl
 * @version [版本号, 2014-11-8]
 */
public class Captcha extends CountDownTimer {
    private Button btn;
    
    /**
     * text 按钮上的文字
     * 
     * @author hl 2014-11-8 下午5:47:45
     */
    private String text;
    
    /**
     * 
     * @param millisInFuture 总秒数
     * @param countDownInterval 隔多久倒计时一次
     * @param btn
     * @author hl 2014-11-8 下午5:47:56
     */
    public Captcha(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
        this.text = btn.getText().toString();
        btn.setClickable(false);
        btn.setPressed(true);
    }
    
    @Override
    public void onTick(long millisUntilFinished)
    {
        btn.setText(millisUntilFinished / 1000 + "秒");
    }
    
    @Override
    public void onFinish() {
        btn.setText("重新发送");
        btn.setClickable(true);
        btn.setPressed(true);
    }
}
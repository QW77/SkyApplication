package com.sky.app.bean;

/**
 * 账户
 * Created by sky on 2017/3/21.
 */

public class BindBean {
    /**
     * 状态码
     */
    public int code;

    /**
     * 信息
     */
    public String msg;

    /**
     * 数据

     */
    public QQWeixinIn retData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public QQWeixinIn getRetData() {
        return retData;
    }

    public void setRetData(QQWeixinIn retData) {
        this.retData = retData;
    }
}
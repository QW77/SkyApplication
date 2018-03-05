package com.sky.app.library.base.bean;

/**
 * Created by sky on 2017/2/13.
 * http请求结果
 */

public class Result<T> {

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
    public T retData;

    public T getData() {
        return retData;
    }

    public void setData(T retData) {
        this.retData = retData;
    }

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
}

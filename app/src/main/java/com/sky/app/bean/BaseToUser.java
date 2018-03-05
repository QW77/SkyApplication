package com.sky.app.bean;

/**
 * 用户信息,请求
 * @author wjx
 *
 */
public class BaseToUser {
    private String to_user_id;//登录标示

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }
}
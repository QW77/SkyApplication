package com.sky.shop.bean;

import java.io.Serializable;

/**
 * 用户信息
 * @author wjx
 *
 */
public class BaseUser implements Serializable{
    private String user_id;//登录标示

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
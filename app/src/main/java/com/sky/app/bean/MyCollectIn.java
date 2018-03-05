package com.sky.app.bean;

/**
 * 我的收藏入参
 * @author wjx
 *
 */
public class MyCollectIn extends Page{
    private String user_id;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
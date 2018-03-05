package com.sky.app.bean;

/**
 * 查询微信或qq账号是否绑定手机号码入参
 */

public class ThidBindIn {
    private String open_id;
    private int type;
    private int user_type;

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

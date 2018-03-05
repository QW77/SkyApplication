package com.sky.shop.bean;

/**
 * 提现记录
 * Created by sky on 2017/3/21.
 */

public class ApplyRecordIn extends Page{
    private String user_id;
    private String state;
    private String create_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}

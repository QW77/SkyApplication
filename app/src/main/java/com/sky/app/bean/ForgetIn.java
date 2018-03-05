package com.sky.app.bean;

/**
 * 忘记密码入参
 * Created by sky on 2017/3/21.
 */

public class ForgetIn {
    private String mobile;
    private String pwd;
    private String user_type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}

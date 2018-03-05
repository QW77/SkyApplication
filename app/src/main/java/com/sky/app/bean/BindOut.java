package com.sky.app.bean;

/**
 * 查询绑定出参
 */

public class BindOut {
    private int qq; //1：已绑定 0：未绑定
    private int weixin;
    private int mobile;
    private String qq_nickname;
    private String qq_open_id;
    private String weixin_open_id;
    private String weixin_nickname;
    private String mobile_num;

    public String getQq_nickname() {
        return qq_nickname;
    }

    public void setQq_nickname(String qq_nickname) {
        this.qq_nickname = qq_nickname;
    }

    public String getQq_open_id() {
        return qq_open_id;
    }

    public void setQq_open_id(String qq_open_id) {
        this.qq_open_id = qq_open_id;
    }

    public String getWeixin_open_id() {
        return weixin_open_id;
    }

    public void setWeixin_open_id(String weixin_open_id) {
        this.weixin_open_id = weixin_open_id;
    }

    public String getWeixin_nickname() {
        return weixin_nickname;
    }

    public void setWeixin_nickname(String weixin_nickname) {
        this.weixin_nickname = weixin_nickname;
    }

    public String getMobile_num() {
        return mobile_num;
    }

    public void setMobile_num(String mobile_num) {
        this.mobile_num = mobile_num;
    }

    public int getQq() {
        return qq;
    }

    public void setQq(int qq) {
        this.qq = qq;
    }

    public int getWeixin() {
        return weixin;
    }

    public void setWeixin(int weixin) {
        this.weixin = weixin;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }
}

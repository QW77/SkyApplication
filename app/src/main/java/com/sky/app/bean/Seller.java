package com.sky.app.bean;

import java.io.Serializable;

/**
 * Created by hongbang on 2017/5/8.
 */
public class Seller implements Serializable{

    private String user_id;
    private String nick_name;
    private String pic_url;
    private String personal_pic;
    private int is_collect;
    private String mobile;
    private String qq;

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPersonal_pic() {
        return personal_pic;
    }

    public void setPersonal_pic(String personal_pic) {
        this.personal_pic = personal_pic;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }
}

package com.sky.app.bean;

import java.util.List;

/**
 * 我的提现账户列表
 * Created by sky on 2017/3/29.
 */

public class AddCommentInfo {
    private String about_order_id;//170505002595",
    private String about_user_id;//商品编号
    private String user_id;//123435",
    private String content;//评价内容",
    private String quality;////质量评分
    private String speed;//2, //速度评分
    private String service;//3, //服务评分
    private int type;
    List<String> pics;//["http://abc.com/1.jpg","http://abc.com/2.jpg"]

    public String getAbout_user_id() {
        return about_user_id;
    }

    public void setAbout_user_id(String about_user_id) {
        this.about_user_id = about_user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAbout_order_id() {
        return about_order_id;
    }

    public void setAbout_order_id(String about_order_id) {
        this.about_order_id = about_order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}

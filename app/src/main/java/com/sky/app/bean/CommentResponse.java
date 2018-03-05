package com.sky.app.bean;

import java.util.List;

/**
 * Created by hongbang on 2017/5/8.
 */

public class CommentResponse {

    String page;
    String rows;
    String all_page;
    String user_id;
    String nick_name;
    String pic_url;
    String comment_content;
    int quality;
    int speed;
    int service;
    String comment_time;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getAll_page() {
        return all_page;
    }

    public void setAll_page(String all_page) {
        this.all_page = all_page;
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

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {

        this.pics = pics;
    }

    List<String> pics;
}

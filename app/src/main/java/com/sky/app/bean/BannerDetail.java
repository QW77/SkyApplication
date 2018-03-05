package com.sky.app.bean;

/**
 * 忘记密码入参
 * Created by sky on 2017/3/21.
 */

public class  BannerDetail{
    private String orderBy;
    private String new_id;
    private String news_title;
    private String news_title_image_url;
    private String news_title_image_jump_url;
    private int other_flag;
    private int state;
    private String create_time;
    private String company_id;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getNew_id() {
        return new_id;
    }

    public void setNew_id(String new_id) {
        this.new_id = new_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_title_image_url() {
        return news_title_image_url;
    }

    public void setNews_title_image_url(String news_title_image_url) {
        this.news_title_image_url = news_title_image_url;
    }

    public String getNews_title_image_jump_url() {
        return news_title_image_jump_url;
    }

    public void setNews_title_image_jump_url(String news_title_image_jump_url) {
        this.news_title_image_jump_url = news_title_image_jump_url;
    }

    public int getOther_flag() {
        return other_flag;
    }

    public void setOther_flag(int other_flag) {
        this.other_flag = other_flag;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}

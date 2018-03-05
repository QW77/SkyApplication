package com.sky.app.bean;

/**
 * Created by hongbang on 2017/5/4.
 * t通过区域id查询呢装饰城
 *
 *  "two_dir_id":"CS10000101",  //区域ID
 "three_dir_name":"装饰城"  //装饰城名称 空则查询所有装饰城
 }
 */

public class SearchUser extends Page{

    private String manufacturer_type_id;
    private String decorative_id;
    private String two_dir_id;
    private String area_id;
    private String nick_name;
    private String one_dir_id;
    private int tp;
    private String user_level_recommend;

    public String getUser_level_recommend() {
        return user_level_recommend;
    }

    public void setUser_level_recommend(String user_level_recommend) {
        this.user_level_recommend = user_level_recommend;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public String getOne_dir_id() {
        return one_dir_id;
    }

    public void setOne_dir_id(String one_dir_id) {
        this.one_dir_id = one_dir_id;
    }

    public String getManufacturer_type_id() {
        return manufacturer_type_id;
    }

    public void setManufacturer_type_id(String manufacturer_type_id) {
        this.manufacturer_type_id = manufacturer_type_id;
    }

    public String getDecorative_id() {
        return decorative_id;
    }

    public void setDecorative_id(String decorative_id) {
        this.decorative_id = decorative_id;
    }

    public String getTwo_dir_id() {
        return two_dir_id;
    }

    public void setTwo_dir_id(String two_dir_id) {
        this.two_dir_id = two_dir_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    @Override
    public String toString() {
        return "SearchUser{" +
                "manufacturer_type_id='" + manufacturer_type_id + '\'' +
                ", decorative_id='" + decorative_id + '\'' +
                ", two_dir_id='" + two_dir_id + '\'' +
                ", area_id='" + area_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", one_dir_id='" + one_dir_id + '\'' +
                ", tp=" + tp +
                ", user_level_recommend=" + user_level_recommend +
                '}';
    }
}
